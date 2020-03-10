package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import static java.lang.String.*;

public class FanRegulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_regulator);
        final BluetoothSocket btSocket = null;
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
                try {
                    btSocket.getOutputStream().write(fanspeed.getBytes());

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                for (byte b : fanspeed.getBytes()) {
                    str += b;
                }




            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FanRegulatorActivity.this, "FAN speed is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
