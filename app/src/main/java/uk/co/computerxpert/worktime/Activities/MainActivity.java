package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;
import uk.co.computerxpert.worktime.data.model.Worktimes;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_Ertek = "TAG: ";
    private TextView mTextMessage;
    private int id = 1;
    private Intent Uj_activity;
    EditText res_WeekWorkday, res_WeekSalary, res_SalaryFactory;
    private int actWeekYear, actYear;


    final Calendar dateTime = Calendar.getInstance(Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        res_WeekWorkday = (EditText) findViewById(R.id.res_WeekWorkdaysBox);
        res_WeekSalary = (EditText) findViewById(R.id.res_WeekSalaryBox);
        res_SalaryFactory = (EditText) findViewById(R.id.res_WeekSalaryBox);
        Button btn_uniqueQueries = (Button) findViewById(R.id.btn_uniqueQueries);
        actWeekYear = dateTime.get(Calendar.WEEK_OF_YEAR);
        actYear = dateTime.get(Calendar.YEAR);

        res_WeekWorkday.setText(workdaysOfWeek().toString());
        salaryOfWeek();

        btn_uniqueQueries.setOnClickListener(this);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(MainActivity.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(MainActivity.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(MainActivity.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    public Integer workdaysOfWeek(){
        String selectQuery =
                " SELECT * FROM Worktime "
                        + " WHERE wt_year = " + actYear + " AND wt_week = " + actWeekYear
                ;
        WorktimesRepo worktimesRepo = new WorktimesRepo();
        List<Worktimes> worktimes_s = worktimesRepo.findWorktime(selectQuery);
        return worktimes_s.size();
    }



    public Integer salaryOfWeek(){
        String selectQuery =
                " SELECT * FROM Worktime "
                        + " WHERE wt_year = " + actYear + " AND wt_week = " + actWeekYear
                ;
        WorktimesRepo worktimesRepo = new WorktimesRepo();
        List<Worktimes> worktimes_s = worktimesRepo.findWorktime(selectQuery);
        return worktimes_s.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_uniqueQueries:
                Log.i(TAG_Ertek, "date");
                break;
        }
    }


}