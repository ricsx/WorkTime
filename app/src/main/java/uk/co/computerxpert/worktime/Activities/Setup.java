package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import uk.co.computerxpert.worktime.R;

public class Setup extends AppCompatActivity implements View.OnClickListener {

    private int id=1;
    private Intent Uj_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button companies = (Button) findViewById(R.id.btn_companies);
        Button wages = (Button) findViewById(R.id.btn_wages);
        Button dev = (Button) findViewById(R.id.btn_dev);

        companies.setOnClickListener(this);
        wages.setOnClickListener(this);
        dev.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    public void onClick(final android.view.View v){
        switch (v.getId()) {
            case R.id.btn_companies:
                Uj_activity = new Intent(Setup.this, CompaniesMan.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
                break;
            case R.id.btn_wages:
                Uj_activity = new Intent(Setup.this, WageMan.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
                break;
            case R.id.btn_dev:
                Uj_activity = new Intent(Setup.this, DeveloperSection.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
                break;
        }
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Setup.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Setup.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Setup.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

}
