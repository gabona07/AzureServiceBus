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

    }
}