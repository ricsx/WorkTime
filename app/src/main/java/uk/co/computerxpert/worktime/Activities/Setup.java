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
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimeRepo;

public class Setup extends AppCompatActivity implements View.OnClickListener {

    private int id=1;
    private Intent Uj_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button companies = (Button) findViewById(R.id.btn_companies);
        Button wages = (Button) findViewById(R.id.btn_wages);

        companies.setOnClickListener(this);
        wages.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    public void onClick(final android.view.View v){
        switch (v.getId()) {
            case R.id.btn_companies:
                Uj_activity = new Intent(Setup.this, Stp_comp.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
                break;
            case R.id.btn_wages:
                Uj_activity = new Intent(Setup.this, Stp_wage.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
                break;
        }
    }


    public void wagesDBDel(View view){

    }

    public void companiesDBDel(View view){
        CompaniesRepo.delete();
    }

    public void worktimeDBDel(View view){
        WorktimeRepo.delete();
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
                    Uj_activity = new Intent(Setup.this, Dashbrd.class);
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
