package com.example.android.samplegit;

import android.app.ProgressDialog;
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
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtUsername, txtPassword;
    ImageButton imgBtnSignIn, imgBtnForgotPassword;
    String id, uname;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InstantiateControl();
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Add_Sputum_Exam.class);
        //Intent intent = new Intent(MainActivity.this, My_Schedule_Patient.class );
        switch (v.getId())
        {
            //sign in
            case R.id.imageButton:
            {
                //progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Loading, Please wait....", true, false);
                //progressDialog.setCancelable(false);
                new WebService().execute();
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
            progressDialog.show();
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
                httpPost = new HttpPost("http://192.168.137.1/login.php");
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
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    JSONObject object = record.getJSONObject(0);
                    id = object.getString("id");
                    uname = object.getString("username");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //progressDialog.dismiss();
                            Intent intent; //= new Intent(MainActivity.this, CollabMenuActivity.class);
                            if(uname.contains("TP"))
                            {
                                //SAMPLE INTENT
                                intent = new Intent(MainActivity.this, PatientInformationModule.class);
                            }
                            else{
                                //SAMPLE INTENT
                                intent = new Intent(MainActivity.this, My_Schedule_Patient.class);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

            }catch (final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
    }

}
