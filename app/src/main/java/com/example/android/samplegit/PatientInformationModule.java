package com.example.android.samplegit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class PatientInformationModule extends AppCompatActivity implements View.OnClickListener{

    Button btnSubmit;
    EditText txtTBCaseNo, txtSputumExamNo;
    Spinner spinnerVisualAppearance, spinnerReading, spinnerDiagnosis;
    ArrayAdapter <String> arrayAdapterVisualAppearance, arrayAdapterReading, arrayAdapterDiagnose;

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
        txtTBCaseNo = (EditText)findViewById(R.id.TxtTBCaseNo);
        txtSputumExamNo = (EditText)findViewById(R.id.TxtSputumExamNo);
        spinnerVisualAppearance = (Spinner)findViewById(R.id.SpinnerVisualAppearance);
        spinnerReading = (Spinner)findViewById(R.id.SpinnerReading);
        spinnerDiagnosis = (Spinner)findViewById(R.id.SpinnerDiagnosis);
    }
    private void PopulateSpinner() {
        String[] VisualAppearance = {"Muco-purulent", "Blood-Stained", "Salivary"};
        String[] Reading = {"0", "+n", "1+", "2+", "3+"};
        String[] Diagnosis = {"POSITIVE", "NEGATIVE"};

        arrayAdapterVisualAppearance = new ArrayAdapter<String>(this, R.layout.activity_add_sputum_exam, VisualAppearance);
        arrayAdapterVisualAppearance.setDropDownViewResource(R.layout.activity_add_sputum_exam);
        spinnerVisualAppearance.setAdapter(arrayAdapterVisualAppearance);

        arrayAdapterReading = new ArrayAdapter<String>(this, R.layout.activity_add_sputum_exam, Reading);
        arrayAdapterReading.setDropDownViewResource(R.layout.activity_add_sputum_exam);
        spinnerReading.setAdapter(arrayAdapterReading);

        arrayAdapterDiagnose = new ArrayAdapter<String>(this, R.layout.activity_add_sputum_exam, Diagnosis);
        arrayAdapterDiagnose.setDropDownViewResource(R.layout.activity_add_sputum_exam);
        spinnerDiagnosis.setAdapter(arrayAdapterDiagnose);
    }
}
