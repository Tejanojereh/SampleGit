package com.example.android.samplegit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtUsername, txtPassword;
    ImageButton imgBtnSignIn, imgBtnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InstantiateControl();
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PatientInformationModule.class);
        switch (v.getId())
        {
            //sign in
            case R.id.imageButton:
            {
                Toast.makeText(this, "Sign In", Toast.LENGTH_SHORT).show();
                //access db
            }break;

            //forgot password
            case R.id.imageButton2:
            {
                //redirect?? ko muna sa add sputum exam.. peace
                Toast.makeText(this, "Forgot Pword", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }break;
        }
    }

    private void InstantiateControl(){
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        imgBtnSignIn = (ImageButton)findViewById(R.id.imageButton);
        imgBtnForgotPassword = (ImageButton)findViewById(R.id.imageButton2);

        imgBtnForgotPassword.setOnClickListener(this);
        imgBtnSignIn.setOnClickListener(this);
    }


}
