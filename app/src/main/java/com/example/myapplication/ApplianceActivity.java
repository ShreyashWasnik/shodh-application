package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ApplianceActivity extends AppCompatActivity {
    Button button_Fan;
    Button button_Cooler;
    Button button_LED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance);
        button_Fan=(Button) findViewById(R.id.button4);
        button_Fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApplianceActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });
    }
}
