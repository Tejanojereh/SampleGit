package com.example.android.samplegit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class View_and_Update_Medication_Progress extends AppCompatActivity implements View.OnClickListener{

    TextView txtOverallProgress, txtMedicationProgress, txtSputumResultAnalysis;

    HttpClient httpclient; HttpResponse httpresponse; HttpPost httppost;
    StringBuffer stringbuffer = null; InputStream inputstream; BufferedReader bufferedreader;
    List<NameValuePair> nameValuePairs;

    String[] ss; byte[] data;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_medication_progress);
        InstantiateControls();
        bundle = getIntent().getExtras();
        new ExecuteTask(this).execute();
        new ExecuteTask3(this).execute();
        new ExecuteTask2(this).execute();
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

        Context context;
        public ExecuteTask(Context con) {context=con;}

        @Override
        protected Object doInBackground(Object[] objects) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TB_Case_No", bundle.getString("id")));
            httppost = new HttpPost("http://10.0.2.2/getPatient_OverallProgress.php");
            try{
                httpclient = new DefaultHttpClient();
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpresponse = httpclient.execute(httppost);

                String[] toReturn = null; int len =0;
                inputstream = httpresponse.getEntity().getContent();
                data = new byte[256];
                stringbuffer = new StringBuffer();
                while (-1 != (len = inputstream.read(data))) {
                    stringbuffer.append(new String(data, 0, len));
                }
                inputstream.close();
                String s = stringbuffer.toString();
                JSONObject jsonObj = new JSONObject(s);
                JSONArray record = jsonObj.getJSONArray("results");
                ss = new String[record.length()];
                for(int i = 0; i< record.length(); i++)
                {
                    JSONObject c = record.getJSONObject(i);
                    ss[i] = c.getString("date");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtOverallProgress.setText( (30*6) - Integer.parseInt(ss[0].toString()) + " days out of " + (30*6) + " days of treatment.");
                    }
                });
            }
            catch(final Exception e) {
                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

    String Dtype, Idate, Ddate;
    public class ExecuteTask2 extends AsyncTask{

        Context context;
        public ExecuteTask2(Context con) {context=con;}

        @Override
        protected Object doInBackground(Object[] objects) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TB_Case_No", bundle.getString("id")));
            httppost = new HttpPost("http://10.0.2.2/retrieve_medicationProgress.php");
            try{
                httpclient = new DefaultHttpClient();
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpresponse = httpclient.execute(httppost);

                String[] toReturn = null; int len =0;
                inputstream = httpresponse.getEntity().getContent();
                data = new byte[256];
                stringbuffer = new StringBuffer();
                while (-1 != (len = inputstream.read(data))) {
                    stringbuffer.append(new String(data, 0, len));
                }
                inputstream.close();
                String s = stringbuffer.toString();
                JSONObject jsonObj = new JSONObject(s);
                JSONArray record = jsonObj.getJSONArray("results");
                ss = new String[record.length()];
//                for(int i = 0; i< record.length(); i++)
//                {
                JSONObject c = record.getJSONObject(0);
                Dtype = c.getString("drug_type");
                Idate = c.getString("Initial_time");
                Ddate = c.getString("due_time");
//                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] re = Eresult.split(" ");
                        txtMedicationProgress.setText("Your medicine intake is "+ Dtype +" \nFirst Intake: "+ Idate +" \nSecond Intake: "+Ddate );
                    }
                });
            }
            catch(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

    String ID, EDate, Eresult;

    //Displaying Sputum Examination Result
    public class ExecuteTask3 extends AsyncTask{

        Context context;
        public ExecuteTask3(Context con) {context=con;}

        @Override
        protected Object doInBackground(Object[] objects) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TB_Case_No", bundle.getString("id")));
            httppost = new HttpPost("http://10.0.2.2/retrieve_SputumExamResult.php");
            try{
                httpclient = new DefaultHttpClient();
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpresponse = httpclient.execute(httppost);

                String[] toReturn = null; int len =0;
                inputstream = httpresponse.getEntity().getContent();
                data = new byte[256];
                stringbuffer = new StringBuffer();
                while (-1 != (len = inputstream.read(data))) {
                    stringbuffer.append(new String(data, 0, len));
                }
                inputstream.close();
                String s = stringbuffer.toString();
                JSONObject jsonObj = new JSONObject(s);
                JSONArray record = jsonObj.getJSONArray("results");
                ss = new String[record.length()];
//                for(int i = 0; i< record.length(); i++)
//                {
                    JSONObject c = record.getJSONObject(0);
                    ID = c.getString("ID");
                    EDate = c.getString("Exam_date");
                    Eresult = c.getString("Result");
//                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] re = Eresult.split(" ");
                        txtSputumResultAnalysis.setText("Date: "+ EDate +" \nAppereance: "+ re[0].toString() +" \nReading: "+re[1].toString()+" \nDiagnosis: "+re[2].toString() );
                    }
                });
            }
            catch(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }
}
