package com.example.android.samplegit;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Menu_TBPartner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    Intent intent;
    Bundle bundle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tbpartner);
        navigationView = (NavigationView) findViewById(R.id.nav_viewtb);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        switch (menuItem.getItemId())
        {

            case R.id.nav_account:
                intent = new Intent(Menu_TBPartner.this, Account_TBPartner.class);
                break;
            case R.id.nav_add:
                intent = new Intent(Menu_TBPartner.this, Add_Sputum_Exam.class);
                break;
            case R.id.nav_log_out:
                intent = new Intent(Menu_TBPartner.this, MainActivity.class);
                break;



        }


        Bundle bundle1= getIntent().getExtras();
        String text=bundle1.getString("id");

        bundle.putString("id",text);
        intent.putExtras(bundle);

        startActivity(intent);

        return false;
    }
}