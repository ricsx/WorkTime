package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uk.co.computerxpert.worktime.R;

public class FirstRunLoadDefaults extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run_load_defaults);


        Button companies = findViewById(R.id.btn_companies2);
        Button wages = findViewById(R.id.btn_wages2);
        Button startToWork = findViewById(R.id.btn_startToWork);
        Button agencies = findViewById(R.id.btn_agencies2);
        Button defShifts = findViewById(R.id.btn_defShifts2);

        companies.setOnClickListener(this);
        wages.setOnClickListener(this);
        agencies.setOnClickListener(this);
        defShifts.setOnClickListener(this);
        startToWork.setOnClickListener(this);

    }


    public void onClick(final android.view.View v){
        switch (v.getId()) {
            case R.id.btn_companies2:
                Uj_activity = new Intent(this, CompaniesMan.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
            case R.id.btn_wages2:
                Uj_activity = new Intent(this, WageMan.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
            case R.id.btn_dev:
                Uj_activity = new Intent(this, DeveloperSection.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
            case R.id.btn_agencies2:
                Uj_activity = new Intent(this, AgenciesMan.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
            case R.id.btn_defShifts2:
                Uj_activity = new Intent(this, DefShiftsMan.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
            case R.id.btn_startToWork:
                Uj_activity = new Intent(this, MainActivity.class);
                Uj_activity.putExtra("firstRunFlag", "1");
                startActivity(Uj_activity);
                break;
        }
    }
}

