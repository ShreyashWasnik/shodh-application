package com.example.myapplication;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import static java.lang.String.*;

public class Regulator extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulator);

        final BluetoothSocket btSocket = null;

        final String OnCommand = "On";
        //final String str = "";
        final String OffCommand = "Off";

        //Code for fan
        final Switch switch1 = findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (btSocket != null) {
                        String str = "";
                        // The toggle is enabled
                        try {
                            btSocket.getOutputStream().write(OnCommand.getBytes());
                            switch1.setText("ON");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (byte b : OnCommand.getBytes()) {
                            str += b;
                        }}
                } else {
                    String str = "";
                    // The toggle is disabled
                    if (btSocket != null) {
                        try {
                            btSocket.getOutputStream().write(OffCommand.getBytes());
                            switch1.setText("OFF");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (byte b : OffCommand.getBytes()) {
                            str += b;
                        }}
                }
            }

        });
        //code for fan speed

        SeekBar simpleSeekBar =  findViewById(R.id.seekBar);
        // perform seek bar change listener event used for getting the progress value
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                String fanspeed;
                fanspeed = new String(valueOf(progressChangedValue));
                String str = "";
                // The toggle is enabled
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write(fanspeed.getBytes());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (byte b : fanspeed.getBytes()) {
                        str += b;
                    }
                }




            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Regulator.this, "FAN speed is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });


        //code for pump state
        final Switch switch2 = findViewById(R.id.switch2);
        final String OnPCommand = "Pump On";
        final String OffPCommand = "Pump Off";

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btSocket != null) {
                    if (isChecked) {
                        String str = "";
                        // The toggle is enabled
                        try {
                            btSocket.getOutputStream().write(OnPCommand.getBytes());
                            switch2.setText("ON");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (byte b : OnPCommand.getBytes()) {
                            str += b;
                        }
                    } else {
                        String str = "";
                        // The toggle is disabled
                        try {
                            btSocket.getOutputStream().write(OffPCommand.getBytes());
                            switch2.setText("OFF");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (byte b : OffPCommand.getBytes()) {
                            str += b;
                        }
                    }}
            }

        });
        /*Button pwrConsu = findViewById(R.id.button7);
        pwrConsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Regulator.this, pwrconsum.class);
                startActivity(i);

            }
        });*/
    }
};