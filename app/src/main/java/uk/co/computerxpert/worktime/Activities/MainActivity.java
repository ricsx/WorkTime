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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;
import uk.co.computerxpert.worktime.data.model.Worktimes;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_Ertek = "TAG: ";
    private TextView mTextMessage;
    private int id = 1;
    private Intent Uj_activity;
    private int actWeekYear, actYear;
    private static DecimalFormat dfToHours = new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private static DecimalFormat dfToWages = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    final Calendar dateTime = Calendar.getInstance(Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        EditText res_WeekWorkday = (EditText) findViewById(R.id.res_WeekWorkdaysBox);
        EditText res_WeekSalary = (EditText) findViewById(R.id.res_WeekSalaryBox);
        EditText res_WeekWorkhours = (EditText) findViewById(R.id.res_WeekWorkhoursBox);
        EditText res_SalaryFactory = (EditText) findViewById(R.id.res_WeekSalaryBox);
        Button btn_uniqueQueries = (Button) findViewById(R.id.btn_uniqueQueries);
        actWeekYear = dateTime.get(Calendar.WEEK_OF_YEAR);
        actYear = dateTime.get(Calendar.YEAR);

        res_WeekWorkday.setText(workdaysOfWeek().toString());
        res_WeekSalary.setText(dfToWages.format(salaryOfWeek()).toString());
        res_WeekWorkhours.setText( dfToHours.format(hoursOfWeek()).toString());

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
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(MainActivity.this, uk.co.computerxpert.worktime.Activities.Worktimes.class);
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



    public Float hoursOfWeek(){
        float workhours = 0;
        String selectQuery= "SELECT * FROM worktime,companies,wage WHERE worktime.wt_comp_id=companies.comp_id " +
                "AND worktime.wt_comp_id=wage.wage_comp_id " +
                " AND wt_year = " + actYear + " AND wt_week = " + actWeekYear
                ;

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.findFullQuerys(selectQuery);
        for(int i=0;i<fullQuerys_s.size();i++){
            float aa = fullQuerys_s.get(i).getwt_startdate();
            float bb = fullQuerys_s.get(i).getwt_enddate();
            float wh = 0;
            // absolut value calculating: double pl; float pl =  -10.0D; float abs2 = Math.abs(value.floatValue());
            if(aa>bb){ wh = (aa - bb) / 3600; }
            else if(aa<bb){ wh = (bb - aa) / 3600; }
            workhours = workhours+wh;
            Log.i(TAG_Ertek, "fullquerys: " + fullQuerys_s.get(i).getcomp_name());
            Log.i(TAG_Ertek, "aa: " + aa);
            Log.i(TAG_Ertek, "bb: " + bb);
            Log.i(TAG_Ertek, "whours: " + wh);
            Log.i(TAG_Ertek, "workhours: " + dfToHours.format(workhours));
        }
        return workhours;
    }


    public Float salaryOfWeek(){
        float workhours=0 , wageOfWeek=0;
        String selectQuery= "select * from worktime,companies,wage where worktime.wt_comp_id=companies.comp_id " +
                "and worktime.wt_comp_id=wage.wage_comp_id " +
                "and wt_year=2018 and wt_week=1";

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.findFullQuerys(selectQuery);
        for(int i=0;i<fullQuerys_s.size();i++){
            float aa = fullQuerys_s.get(i).getwt_startdate();
            float bb = fullQuerys_s.get(i).getwt_enddate();
            float wh = 0;
            if(aa>bb){ wh = (aa - bb) / 3600; }
            else if(aa<bb){ wh = (bb - aa) / 3600; }
            String wage_val = fullQuerys_s.get(i).getwage_val();
            workhours = workhours+wh;
            float wageOfDay = wh * Float.parseFloat(wage_val);
            wageOfWeek = wageOfWeek + wageOfDay;
            Log.i(TAG_Ertek, "===========================================================");
            Log.i(TAG_Ertek, "compname: " + fullQuerys_s.get(i).getcomp_name());
            Log.i(TAG_Ertek, "aa: " + aa);
            Log.i(TAG_Ertek, "bb: " + bb);
            Log.i(TAG_Ertek, "wh: " + wh);
            Log.i(TAG_Ertek, "wage: " + wage_val);
            Log.i(TAG_Ertek, "napi : " + wageOfDay);
        }
            Log.i(TAG_Ertek, "===========================================================");
            Log.i(TAG_Ertek, "\n workhours: " + dfToHours.format(workhours));
            Log.i(TAG_Ertek, "\n wageOfWeek: " + dfToWages.format(wageOfWeek));
            Log.i(TAG_Ertek, "===========================================================");

        return wageOfWeek;
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