package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    Button myButton,logout;
    BluetoothAdapter bluetoothAdapter;
    //FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,LoginActivity.class));

        }*/

        //FirebaseUser user = firebaseAuth.getCurrentUser();


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        myButton=(Button) findViewById(R.id.button4);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,SecondActivity.class);
                startActivity(i);
            }
        });
        logout=(Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
    public void On (View view){
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
            Toast.makeText(this, "Bluetooth On", Toast.LENGTH_SHORT).show();
        }


    }
    public void Off(View view){
        bluetoothAdapter.disable();
        Toast.makeText(this, "Bluetooth Off", Toast.LENGTH_SHORT).show();

    }

}
