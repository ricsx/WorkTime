package uk.co.computerxpert.worktime.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
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
    private int id = 1;
    private Intent Uj_activity, Uj_activity2;
    private int actWeekYear, actYear;
    private static DecimalFormat dfToHours = new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private static DecimalFormat dfToWages = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    final Calendar dateTime = Calendar.getInstance(Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        EditText res_WeekWorkday = (EditText) findViewById(R.id.res_WeekWorkdaysBox);
        EditText res_WeekSalary = (EditText) findViewById(R.id.res_WeekSalaryBox);
        EditText res_WeekWorkhours = (EditText) findViewById(R.id.res_WeekWorkhoursBox);
        Button btn_uniqueQueries = (Button) findViewById(R.id.btn_uniqueQueries);

        // ONLY FOR TESTING !!!!
        actWeekYear = dateTime.get(Calendar.WEEK_OF_YEAR)-1;
        actYear = dateTime.get(Calendar.YEAR);
        // END OF TESTING AREA

        // NON-TESTING AREA, MUST RECOVER !!!!
/*        actWeekYear = dateTime.get(Calendar.WEEK_OF_YEAR);
        actYear = dateTime.get(Calendar.YEAR);
*/

        //MyActivity();

        res_WeekWorkday.setText(workdaysOfWeek().toString());
        res_WeekWorkhours.setText( dfToHours.format(hoursOfWeek()).toString());
        res_WeekSalary.setText(dfToWages.format(salaryOfWeek()).toString());

        btn_uniqueQueries.setOnClickListener(this);

    }

    public class MyActivity extends Activity {

    private TableLayout tableLayout;

    /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        for (int i = 0; i < 5; i++) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
            TextView history_display_no = (TextView) tableRow.findViewById(R.id.first);
            TextView history_display_date = (TextView) tableRow.findViewById(R.id.second);
            TextView history_display_orderid = (TextView) tableRow.findViewById(R.id.third);
            TextView history_display_quantity = (TextView) tableRow.findViewById(R.id.forth);

            history_display_no.setText("" + (i + 1));
            history_display_date.setText("2014-02-05");
            history_display_orderid.setText("S0" + (i + 1));
            history_display_quantity.setText("" + (20 + (i + 1)));
            tableLayout.addView(tableRow);
        }
    }
    */
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

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(selectQuery);
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
        String selectQuery= "SELECT * FROM worktime,companies,wage WHERE worktime.wt_comp_id=companies.comp_id " +
                "AND worktime.wt_comp_id=wage.wage_comp_id " +
                "AND wt_year= " + actYear + " AND wt_week= " + actWeekYear
                ;

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(selectQuery);
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
            Uj_activity2 = new Intent(MainActivity.this, uk.co.computerxpert.worktime.Activities.listMaker.class);
            Uj_activity2.putExtra("sessid", id);
            startActivity(Uj_activity2);
            break;
        }
    }


}