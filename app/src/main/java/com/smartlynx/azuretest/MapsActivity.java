package com.smartlynx.azuretest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setupMap();
        try {
            receiveMessages();
        } catch (InterruptedException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void receiveMessages() throws InterruptedException {
        CountDownLatch countdownLatch = new CountDownLatch(1);
        String connectionString = "Endpoint=sb://parking-service-bus.servicebus.windows.net/;SharedAccessKeyName=smartlynx_test;SharedAccessKey=jHWpUrEu9p3G8vMDOrLR6xwzW1IPqWb5LKv5+g0dhpg=;EntityPath=smartlynx_test";
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .topicName("smartlynx_test")
                .subscriptionName("smartlynx_test")
                .processMessage(QueueTest::processMessage)
                .processError(context -> processError(context, countdownLatch))
                .buildProcessorClient();
        System.out.println("Starting the processor");
        processorClient.start();
    }

    private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
                || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
                || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
                    reason, exception.getMessage());


            countdownLatch.countDown();
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            System.out.printf("Message lock lost for message: %s%n", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unable to sleep for period of time");
            }
        } else {
            System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
                    reason, context.getException());
        }
    }

    private void setupLocations() {
        String jsonFileString = getJsonFromAssets();
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Location>>() { }.getType();

        List<Location> locations = gson.fromJson(jsonFileString, listUserType);
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            LatLng latLng = new LatLng(Double.parseDouble(location.latitude), Double.parseDouble(location.longitude));
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.491711, 19.055805)));
            map.setMinZoomPreference(16f);
            map.animateCamera(CameraUpdateFactory.zoomTo(18f));
            showMarkers(latLng);
        }
    }

    private void showMarkers(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        map.addMarker(markerOptions).setIcon(setVectorDrawable(getApplicationContext(), R.drawable.ic_grey));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.setIcon(setVectorDrawable(getApplicationContext(), R.drawable.ic_blue));
                Intent intent = new Intent();
                return false;
            }
        });
    }

    private BitmapDescriptor setVectorDrawable(Context context, Integer vectorDrawableId)  {
        VectorDrawable vectorDrawable = (VectorDrawable) ContextCompat.getDrawable(context, vectorDrawableId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private String getJsonFromAssets() {
        String jsonString;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
//        setupLocations();
//        showSectors();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showSectors() {
        Polygon sectorOne = map.addPolygon(new PolygonOptions().add(new LatLng(47.512690, 19.048985), new LatLng(47.511223, 19.053243), new LatLng(47.510753, 19.053077), new LatLng(47.510941, 19.051729), new LatLng(47.508462, 19.050938), new LatLng(47.508678, 19.049406), new LatLng(47.509624, 19.049762), new LatLng(47.509878, 19.048048)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.510134, 19.051406), 16f));
        int transparentOrange = Color.argb(0.5f, 20f, 255f, 175f);
        sectorOne.setFillColor(transparentOrange);
        sectorOne.setStrokeColor(transparentOrange);

        Polygon sectorTwo = map.addPolygon(new PolygonOptions().add(new LatLng(47.511223, 19.053243), new LatLng(47.510432, 19.055278), new LatLng(47.507871, 19.054918), new LatLng(47.507617, 19.051935), new LatLng(47.509064, 19.052369), new LatLng(47.509285, 19.051202), new LatLng(47.510178, 19.051503), new LatLng(47.509991, 19.052688)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.509586, 19.053491), 16f));
        int transparentPurple = Color.argb(0.5f, 200f, 175f, 50f);
        sectorTwo.setFillColor(transparentPurple);
        sectorTwo.setStrokeColor(transparentPurple);

        Polygon sectorThree = map.addPolygon(new PolygonOptions().add(new LatLng(47.513540, 19.046377), new LatLng(47.512690, 19.048985), new LatLng(47.509878, 19.048048), new LatLng(47.509624, 19.049762), new LatLng(47.509511, 19.049743), new LatLng(47.509949, 19.046954), new LatLng(47.509355, 19.046788), new LatLng(47.509146, 19.045348)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.511159, 19.047902), 16f));
        int transparentYellowGreen = Color.parseColor("#1C00ff00");
        sectorThree.setFillColor(transparentYellowGreen);
        sectorThree.setStrokeColor(transparentYellowGreen);

        Polygon sectorFour = map.addPolygon(new PolygonOptions().add(new LatLng(47.509816, 19.047863), new LatLng(47.509783, 19.048053), new LatLng(47.508670, 19.049447), new LatLng(47.508277, 19.052162), new LatLng(47.507581, 19.051907), new LatLng(47.507538, 19.051872), new LatLng(47.507873, 19.054999), new LatLng(47.506443, 19.054951), new LatLng(47.505919, 19.049031)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.506834, 19.052058), 16f));
        int transparentPink = Color.argb(0.5f, 50f, 175f, 50f);
        sectorFour.setFillColor(transparentPink);
        sectorFour.setStrokeColor(transparentPink);

        Polygon sectorFive = map.addPolygon(new PolygonOptions().add(new LatLng(47.505745, 19.045064), new LatLng(47.506448, 19.054982), new LatLng(47.503571, 19.054909), new LatLng(47.503497, 19.053890), new LatLng(47.504601, 19.053663), new LatLng(47.504045, 19.045267)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.505243, 19.050182), 16f));
        int transparentBeige = Color.argb(0.5f, 50f, 50f, 50f);
        sectorFive.setFillColor(transparentBeige);
        sectorFive.setStrokeColor(transparentBeige);


        Polygon sectorSix = map.addPolygon(new PolygonOptions().add(new LatLng(47.504070, 19.044845), new LatLng(47.504678, 19.053746), new LatLng(47.502641, 19.054162), new LatLng(47.501583, 19.045654)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.503308, 19.050654), 16f));
        int transparentGreen = Color.argb(0.5f, 155f, 75f, 255f);
        sectorSix.setFillColor(transparentGreen);
        sectorSix.setStrokeColor(transparentGreen);

        Polygon sectorSeven = map.addPolygon(new PolygonOptions().add(new LatLng(47.501697, 19.045737), new LatLng(47.502642, 19.054838), new LatLng(47.499208, 19.054746), new LatLng(47.498751, 19.048198), new LatLng(47.500302, 19.046256)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.500725, 19.050789), 16f));
        int transparentYellow = Color.argb(0.5f, 25f, 175f, 255f);
        sectorSeven.setFillColor(transparentYellow);
        sectorSeven.setStrokeColor(transparentYellow);

        Polygon sectorEight = map.addPolygon(new PolygonOptions().add(new LatLng(47.499481, 19.046428), new LatLng(47.498710, 19.048456), new LatLng(47.492501, 19.053581), new LatLng(47.491600, 19.051003)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.496084, 19.049600), 16f));
        int transparentBlue = Color.argb(0.5f, 165f, 125f, 50f);
        sectorEight.setFillColor(transparentBlue);
        sectorEight.setStrokeColor(transparentBlue);

        Polygon sectorNine = map.addPolygon(new PolygonOptions().add(new LatLng(47.498964, 19.051221), new LatLng(47.499282, 19.055035), new LatLng(47.495910, 19.059551), new LatLng(47.493442, 19.060559), new LatLng(47.491362, 19.054205)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.494624, 19.055812), 16f));
        int transparentPastelPink = Color.argb(0.5f, 185f, 150f, 100f);
        sectorNine.setFillColor(transparentPastelPink);
        sectorNine.setStrokeColor(transparentPastelPink);

        Polygon sectorTen = map.addPolygon(new PolygonOptions().add(new LatLng(47.491585, 19.050841), new LatLng(47.492501, 19.053581), new LatLng(47.487317, 19.058133), new LatLng(47.486606, 19.056773)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.489822, 19.054400), 16f));
        int transparentLilac = Color.argb(0.5f, 0f, 150f, 100f);
        sectorTen.setStrokeColor(transparentLilac);
        sectorTen.setFillColor(transparentLilac);

        Polygon sectorEleven = map.addPolygon(new PolygonOptions().add(new LatLng(47.491439, 19.054146), new LatLng(47.493517, 19.060537), new LatLng(47.489373, 19.061660), new LatLng(47.487786, 19.057551)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.490339, 19.058783), 16f));
        int transparentPastelBlue = Color.argb(0.5f, 200f, 125f, 50f);
        sectorEleven.setFillColor(transparentPastelBlue);
        sectorEleven.setStrokeColor(transparentPastelBlue);
    }

    private void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}