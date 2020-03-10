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
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class RegulatorActivity extends AppCompatActivity {


    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button Speed_1, Speed_2, Speed_3,Speed_4,PowerON1,PowerOFF1;

    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ProgressDialog progress;
    private boolean isBtConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulator2);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra("EXTRA_ADDRESS");
        msg(address);

        //call the widgtes
        Speed_1 = (Button) findViewById(R.id.speed1);
        Speed_2 = (Button) findViewById(R.id.speed2);
        Speed_3 = (Button) findViewById(R.id.speed3);
        Speed_4 = (Button) findViewById(R.id.speed4);
        PowerON1 = (Button)findViewById(R.id.PowerON1);
        PowerOFF1= (Button)findViewById(R.id.PowerOFF1);



        new ConnectBT().execute(); //Call the class to connect




        PowerON1.setOnClickListener(new View.OnClickListener() {
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

        PowerOFF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("OFF");
                if (btSocket != null) {
                    msg("Power OFF");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("7".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        Speed_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("1");

                if (btSocket != null) {
                    msg("Speed 1");
                    try {

                        btSocket.getOutputStream().write("2".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }      //method to turn on
            }
        });


        Speed_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("2");

                if (btSocket != null) {
                    msg("Speed 2");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("3".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }      //method to turn on
            }
        });

        Speed_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("3");

                if (btSocket != null) {
                    msg("Speed 3");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("4".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }      //method to turn on
            }
        });

        Speed_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("4");

                if (btSocket != null) {
                    msg("Speed 4");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write("5".getBytes());

                    } catch (IOException e) {
                        msg("Error");
                    }
                }      //method to turn on
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
            progress = ProgressDialog.show(RegulatorActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
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
