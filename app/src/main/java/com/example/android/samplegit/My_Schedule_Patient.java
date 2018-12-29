package com.example.android.samplegit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class My_Schedule_Patient extends AppCompatActivity{

    TextView appt_date;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_tbpartner);
        appt_date=findViewById(R.id.appointment_date);


    }
}
