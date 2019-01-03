package com.example.android.samplegit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PatientInformationModule extends AppCompatActivity implements View.OnClickListener{

    Button btnSubmit;
    //EditText txtTBCaseNo, txtSputumExamNo;
    Spinner spinnerVisualAppearance, spinnerReading, spinnerDiagnosis, spinnerPatient;
    ArrayAdapter <CharSequence> arrayAdapterVisualAppearance, arrayAdapterReading, arrayAdapterDiagnose, arrayAdapterPatient;

    HttpClient httpclient; HttpResponse httpresponse; HttpPost httppost;
    StringBuffer stringbuffer = null; InputStream inputstream; BufferedReader bufferedreader;
    List<NameValuePair> nameValuePairs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sputum_exam);
        InstantiateControls();
        PopulateSpinner();

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //insert to DB

    }

    private void InstantiateControls(){
        btnSubmit = (Button)findViewById(R.id.BtnSubmit);
//        txtTBCaseNo = (EditText)findViewById(R.id.TxtTBCaseNo);
//        txtSputumExamNo = (EditText)findViewById(R.id.TxtSputumExamNo);
        spinnerVisualAppearance = (Spinner)findViewById(R.id.SpinnerVisualAppearance);
        spinnerReading = (Spinner)findViewById(R.id.SpinnerReading);
        spinnerDiagnosis = (Spinner)findViewById(R.id.SpinnerDiagnosis);
        spinnerPatient = (Spinner)findViewById(R.id.SpinnerPatient);

        httpclient = new DefaultHttpClient();

    }
    private void PopulateSpinner() {
        String[] VisualAppearance = {"Muco-purulent", "Blood-Stained", "Salivary"};
        String[] Reading = {"0", "+n", "1+", "2+", "3+"};
        String[] Diagnosis = {"POSITIVE", "NEGATIVE"};

        arrayAdapterVisualAppearance = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, VisualAppearance);
        arrayAdapterVisualAppearance.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerVisualAppearance.setAdapter(arrayAdapterVisualAppearance);

        arrayAdapterReading = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Reading);
        arrayAdapterReading.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerReading.setAdapter(arrayAdapterReading);

        arrayAdapterDiagnose = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Diagnosis);
        arrayAdapterDiagnose.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDiagnosis.setAdapter(arrayAdapterDiagnose);

        //TBPatient Spinner Populate
        new ExecuteTask(this).execute();
    }

    public String[] ReadResponse() {
        try {
            String line =""; String[] toReturn; int index = 0;
            inputstream = httpresponse.getEntity().getContent();
            toReturn = new String[inputstream.available()]; //get the length. do it before reading the stream
            bufferedreader = new BufferedReader(new InputStreamReader(inputstream));

            if(bufferedreader.ready()) {
                while ((line = bufferedreader.readLine()) != null) {
                    toReturn[index] = line;
                    index++;
                }
            }
            else
            {
                toReturn[index] = "No Assigned Patient";
            }

            return toReturn;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class ExecuteTask extends AsyncTask {

        private Context context;

        public ExecuteTask(Context con) {
            context = con;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String[] patientList;
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("TP_ID", "TP000001"));
            httppost = new HttpPost("http://localhost/TBCareService/retrieveAssignedPatient.php");
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpresponse = httpclient.execute(httppost);

                if((patientList = ReadResponse()) != null) {
                    arrayAdapterPatient = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item, patientList);
                    arrayAdapterPatient.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerPatient.setAdapter(arrayAdapterPatient);
                }

            }
            catch (UnsupportedEncodingException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
            return null;
        }
    }
}
