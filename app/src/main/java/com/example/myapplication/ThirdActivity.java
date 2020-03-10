package com.example.myapplication;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;

public class ThirdActivity extends AppCompatActivity {

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button btnSend, btnDis, myButton,button_Fan,button_Cooler,button_LED;
    EditText message;
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ProgressDialog progress;
    private boolean isBtConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra("EXTRA_ADDRESS");
        msg(address);

        //call the widgtes
        //btnSend = (Button) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.text);
        btnDis = (Button) findViewById(R.id.disc);
        button_Cooler = (Button)findViewById(R.id.button_Cooler);
        button_Fan = (Button)findViewById(R.id.button_Fan);
        button_LED = (Button)findViewById(R.id.button_LED);



        button_Fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThirdActivity.this,RegulatorActivity.class);
                i.putExtra("EXTRA_ADDRESS", address);
                try {
                    btSocket.close(); //close connection
                } catch (IOException e) {
                    msg("Error");
                }
                startActivity(i);
            }
        });
        button_Cooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThirdActivity.this,CoolerRegulatorActivity.class);
                i.putExtra("EXTRA_ADDRESS", address);
                try {
                    btSocket.close(); //close connection
                } catch (IOException e) {
                    msg("Error");
                }
                startActivity(i);
            }
        });
        button_LED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThirdActivity.this,LEDRegulatorActivator.class);
                i.putExtra("EXTRA_ADDRESS", address);
                try {
                    btSocket.close(); //close connection
                } catch (IOException e) {
                    msg("Error");
                }
                startActivity(i);
            }
        });




      /*  myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ThirdActivity.this, RegulatorActivity.class);
                i.putExtra("EXTRA_ADDRESS", address);
                try {
                    btSocket.close(); //close connection
                } catch (IOException e) {
                    msg("Error");
                }
                startActivity(i);
            }
        });*/

        new ConnectBT().execute(); //Call the class to connect

        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("ON");

                if (btSocket != null) {
                    msg("SOCKET ON");
                    try {
                        String str = new String();
                        btSocket.getOutputStream().write(message.getText().toString().getBytes());
                        for (byte b : message.getText().toString().getBytes()) {
                            str += b;
                        }
                        Toast.makeText(ThirdActivity.this, str, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        msg("Error");
                    }
                }      //method to turn on
            }
        });*/


        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btSocket != null) //If the btSocket is busy
                {
                    try {
                        btSocket.close(); //close connection
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                finish(); //return to the first layout
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
            progress = ProgressDialog.show(ThirdActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
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
