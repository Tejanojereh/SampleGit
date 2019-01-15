package com.example.android.samplegit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class View_and_Update_Medication_Progress extends AppCompatActivity implements View.OnClickListener{

    TextView txtOverallProgress, txtMedicationProgress, txtSputumResultAnalysis;

    HttpClient httpclient; HttpResponse httpresponse; HttpPost httppost;
    StringBuffer stringbuffer = null; InputStream inputstream; BufferedReader bufferedreader;
    List<NameValuePair> nameValuePairs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_medication_progress);
        InstantiateControls();
        new ExecuteTask().execute();
    }

    @Override
    public void onClick(View v) {

    }

    public void InstantiateControls() {
        txtOverallProgress = (TextView)findViewById(R.id.TxtOverallProgress);
        txtMedicationProgress = (TextView)findViewById(R.id.TxtMedicationProgress);
        txtSputumResultAnalysis = (TextView)findViewById(R.id.TxtSputumResultAnalysis);
    }

    public class ExecuteTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TB_Case_No", "TB10981"));
            httppost = new HttpPost("http://10.0.2.2/getPatient_OverallProgress.php");
            try{
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpresponse = httpclient.execute(httppost);


            }
            catch(Exception e) {

            }
            return null;
        }
    }
}
