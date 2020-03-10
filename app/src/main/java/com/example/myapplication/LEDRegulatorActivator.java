package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import org.w3c.dom.Text;

public class LEDRegulatorActivator extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ProgressDialog progress;
    private boolean isBtConnected = false;
    TextView textView;
    ProgressBar progressBar;
    SeekBar seekBar;
    Button LedON1,LedOFF1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledregulator_activator);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra("EXTRA_ADDRESS");
        msg(address);

        textView = (TextView)findViewById(R.id.progressText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        LedON1 = (Button)findViewById(R.id.LedON1);
        LedOFF1 = (Button)findViewById(R.id.LedOFF1);

        new ConnectBT().execute(); //Call the class to connect




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText(""+progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        LedON1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("ON");
                if (btSocket != null) {
                    msg("Power ON");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("6".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }

        });

        LedOFF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("OFF");
                if (btSocket != null) {
                    msg("Power OFF");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("6".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }

        });


    }


    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LEDRegulatorActivator.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }


        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
