package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button myButton;
    EditText user;
    EditText pass;
    TextView login;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        myButton= (Button)findViewById(R.id.button);
        user= (EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.pass);
        login=(TextView)findViewById(R.id.login);
        progressDialog= new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();

            startActivity(new Intent(getApplicationContext(),Main2Activity.class));

        }
        myButton.setOnClickListener(this);
        login.setOnClickListener(this);





            }
            private void registerUser(){
        String email = user.getText().toString().trim();
        String password= pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
                    Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT ).show();
return;
                }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();
            return;
                }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(MainActivity.this,"Registered Successfully", Toast.LENGTH_SHORT).show();

                           finish();
                           startActivity(new Intent(getApplicationContext(),Main2Activity.class));

                       }

                   else{
                       Toast.makeText(MainActivity.this,"Could not register...", Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }


    @Override
    public void onClick(View v) {
if (v==myButton){
    registerUser();
    }
if(v==login){
    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
}

}


}



