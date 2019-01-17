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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_medication_progress);
        InstantiateControls();
        new ExecuteTask(this).execute();
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
            nameValuePairs.add(new BasicNameValuePair("TB_Case_No", "TB10981"));
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
                JSONArray record = jsonObj.getJSONArray("treatment_date");
                ss = new String[record.length()];
                for(int i = 0; i< record.length(); i++)
                {
                    JSONObject c = record.getJSONObject(i);
                    ss[i] = c.getString("results");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtOverallProgress.setText( Integer.parseInt(ss[0].toString()) - (30*6) + " days out of " + (30*6) + " days of treatment.");
                    }
                });
            }
            catch(Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}
