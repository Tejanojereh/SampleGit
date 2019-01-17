package com.example.android.samplegit;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class My_Schedule_Patient extends AppCompatActivity{

    TextView appt_date;
    TextView med_date;
    TextView datenow;
    String P_ID="1";
    WebView wv;
    Boolean linkCheck;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule_patient);
        appt_date=findViewById(R.id.appointment_date);
        med_date=findViewById(R.id.medicine_date);
        datenow=findViewById(R.id.date);

        Date currentTime= Calendar.getInstance().getTime();
        datenow.setText(currentTime.toString());


        new WebService_Medication().execute();

    }


  public  class WebService_Medication extends AsyncTask
    {
        @Override
        protected Void doInBackground(Object... objects)
        {
            byte[] data;
            HttpPost httpPost;
            StringBuffer buffer;
            HttpResponse response;
            HttpClient httpclient;
            InputStream inputStream;
            final String message;

            List<NameValuePair> nameValuePairs;
            nameValuePairs= new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("P_ID","1"));

            try
            {
                httpclient = new DefaultHttpClient();
                httpPost = new HttpPost("http://192.168.137.1/retrieve_medication.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response=httpclient.execute(httpPost);
                inputStream=response.getEntity().getContent();

                data= new byte[256];
                buffer=new StringBuffer();
                int len=0;

                while(-1!=(len=inputStream.read(data))) {
                    buffer.append(new String(data, 0, len));


                }
                String s= buffer.toString();
                JSONObject jsonObj= new JSONObject(s);
                JSONArray record = jsonObj.getJSONArray("results");
                final JSONObject c = record.getJSONObject(0);
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {


                        try {
                            med_date.setText("Medicine Intake Schedule:"+ c.getString("M_InitialTime").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch (final Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(My_Schedule_Patient.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
            }



        }
    class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
    }



}
