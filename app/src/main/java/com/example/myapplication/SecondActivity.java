package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/* import android.support.v7.app.AppCompatActivity; */
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {

    int REQUEST_ENABLE_BT=1;
    ListView pairedlistView;
    /* ListView unpairedlistView; */
    ArrayList<String> pairedlist = new ArrayList<String>();
    /* ArrayList<String> unpairedlist = new ArrayList<String>(); */
    public static String DEVICE_ADDRESS = "device_address";
    private IntentFilter filter;
    private OutputStream outputStream;
    private InputStream inStream;
    private EditText message;
    private Button send;
    private static final String TAG = "MainActivity";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pairedlistView = (ListView) findViewById(R.id.pairedlist);
        //unpairedlistView = (ListView) findViewById(R.id.unpairedlist);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();


        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                pairedlist.add(deviceName);
            }
        }

        ArrayAdapter<String> paireddeviceInfo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pairedlist);
        pairedlistView.setAdapter(paireddeviceInfo);


        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);

       /*
        ArrayAdapter<String> unpaireddeviceInfo = new ArrayAdapter<String>(this, android.R.layout.list_content, unpairedlist);
         unpairedlistView.setAdapter(unpaireddeviceInfo);
       */


        pairedlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String select = (String)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row

                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice bt : pairedDevices) {
                        //list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address


                        if (bt.getName().equals(select)) {
                            String address = bt.getAddress();
                            // Make an intent to start next activity.
                            Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                            //Change the activity.
                            i.putExtra("EXTRA_ADDRESS", address); //this will be received at ledControl (class) Activity
                            startActivity(i);

                        }


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                /*
                String deviceHardwareAddress = device.getAddress(); // MAC address
                unpairedlist.add("\n"+deviceName+"\n");
                */
            }
        }
    };
}
