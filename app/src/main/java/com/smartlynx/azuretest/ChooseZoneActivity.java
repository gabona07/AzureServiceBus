package com.smartlynx.azuretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseZoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_zone);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        Button sectorOneButton = findViewById(R.id.button1);
        Button sectorTwoButton = findViewById(R.id.button2);
        Button sectorThreeButton = findViewById(R.id.button3);
        Button sectorFourButton = findViewById(R.id.button4);
        Button sectorFiveButton = findViewById(R.id.button5);
        Button sectorSixButton = findViewById(R.id.button6);
        Button sectorSevenButton = findViewById(R.id.button7);
        Button sectorEightButton = findViewById(R.id.button8);
        Button sectorNineButton = findViewById(R.id.button9);
        Button sectorTenButton = findViewById(R.id.button10);
        Button sectorElevenButton = findViewById(R.id.button11);

        sectorOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.510134);
                intent.putExtra("longitude", 19.051406);
                startActivity(intent);
            }
        });

        sectorTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.509586);
                intent.putExtra("longitude", 19.053491);
                startActivity(intent);
            }
        });

        sectorThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.511159);
                intent.putExtra("longitude", 19.047902);
                startActivity(intent);
            }
        });

        sectorFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.506834);
                intent.putExtra("longitude", 19.052058);
                startActivity(intent);
            }
        });

        sectorFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.505243);
                intent.putExtra("longitude", 19.050182);
                startActivity(intent);
            }
        });

        sectorSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.503308);
                intent.putExtra("longitude", 19.050654);
                startActivity(intent);
            }
        });

        sectorSevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.500725);
                intent.putExtra("longitude", 19.050789);
                startActivity(intent);
            }
        });

        sectorEightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.496084);
                intent.putExtra("longitude", 19.049600);
                startActivity(intent);
            }
        });

        sectorNineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.494624);
                intent.putExtra("longitude", 19.055812);
                startActivity(intent);
            }
        });

        sectorTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.489822);
                intent.putExtra("longitude",  19.054400);
                startActivity(intent);
            }
        });

        sectorElevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseZoneActivity.this, MapsActivity.class);
                intent.putExtra("latitude", 47.490339);
                intent.putExtra("longitude", 19.058783);
                startActivity(intent);
            }
        });

    }
}