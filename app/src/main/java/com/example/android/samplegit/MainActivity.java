package com.example.android.samplegit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


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

    class WebService extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpPost httpPost;
            StringBuffer buffer = null;
            HttpResponse response;
            HttpClient httpClient;
            InputStream inputStream;
            final String message;

            List<NameValuePair> nameValuePairs;
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", txtUsername.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", txtPassword.getText().toString()));

            try {
                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost("http://localhost/TBCareService/login.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                response = httpClient.execute(httpPost);


            }catch (Exception e) {

            }

            return null;
        }
    }

}
