package com.example.android.samplegit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtUsername, txtPassword;
    ImageButton imgBtnSignIn, imgBtnForgotPassword;
    String id, uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InstantiateControl();
    }
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(MainActivity.this, ForgotPassword_tbpartner.class );
        switch (v.getId())
        {
            //sign in
            case R.id.imageButton:
            {

                new WebService().execute();
            }break;

            //forgot password
            case R.id.imageButton2:
            {

                Toast.makeText(this, "Forgot Password", Toast.LENGTH_SHORT).show();
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
            byte data[];
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





              httpPost = new HttpPost("http://tbcarephp.azurewebsites.net/login.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                response = httpClient.execute(httpPost);
                inputStream = response.getEntity().getContent();
                data = new byte[256];
                buffer = new StringBuffer();
                int len = 0;

                while(-1 != (len=inputStream.read(data))) {
                    buffer.append(new String (data, 0, len));
                }

                message = buffer.toString();
                JSONObject jsonObj = new JSONObject(message);
                org.json.JSONArray record = jsonObj.getJSONArray("results");
             //   inputStream.close();

                if(record.length() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        //    progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    JSONObject object = record.getJSONObject(0);
                    id = object.getString("id");
                     object = record.getJSONObject(1);
                    uname = object.getString("username");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent;
                            if(uname.contains("TP") )
                                intent = new Intent(MainActivity.this, Menu_TBPartner.class);

                            else {
                                intent = new Intent(MainActivity.this, Menu_Patient.class);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("id", uname);
                            intent.putExtras(bundle);
                            txtPassword.setText(" ");
                            txtUsername.setText(" ");
                            startActivity(intent);

                        }
                    });
                }

            }catch (final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Incorrect username or password!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
    }

}
