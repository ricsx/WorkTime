package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;

import static uk.co.computerxpert.worktime.Common.Common.dformat;
import static uk.co.computerxpert.worktime.Common.Common.settingTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Intent Uj_activity;
    int id = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        //noinspection StatementWithEmptyBody,EqualsBetweenInconvertibleTypes
        if(settingTest("firstrun").equals("") || settingTest("firstrun").equals(0)) {
            Settings settings = new Settings();
            settings.set_settings_name("firstrun");
            settings.set_settings_val("1");
            SettingsRepo.insert(settings);
            Uj_activity = new Intent(MainActivity.this, FirstRunLoadDefaults.class);
            startActivity(Uj_activity);
        }else{}

        addHeaders();
        addData();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        floatingActionButton();
    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor, int txtsize) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(10, 10, 10, 10);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setTextSize(12);
        tv.setOnClickListener(this);
        tv.setGravity(1);
        if(txtsize != 0) tv.setTextSize(txtsize);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(6, 6, 0, 0);
        return params;
    }


    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }


    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.setBackgroundColor(Color.WHITE);
            tr.addView(getTextView(0, "", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16));
            tr.addView(getTextView(1, "SHIFT", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16));
            tr.addView(getTextView(2, "HOURS", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16 ));
            tr.addView(getTextView(3, "WAGE", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16 ));
       tl.addView(tr, getTblLayoutParams());
    }


    /**
     * This function add the data to the table
     **/
    public void addData() {

        Integer rowcolor;
        Integer maxStartWeek = maxStartWeek(), maxYear = maxYear(), dMaxWeek = yearValidator();
        Integer numberOfWeeks;
        String whereYear = "";

        if(settingTest("viewNumWeeks").equals("")||settingTest("viewNumWeeks").equals(null)){
            numberOfWeeks = 0;
        }else{
            numberOfWeeks = Integer.parseInt(settingTest("viewNumWeeks"));

        }
        if(maxStartWeek-numberOfWeeks>dMaxWeek) {
            Integer gWeek = dMaxWeek+52-numberOfWeeks+1;
            whereYear = "AND (wt_year=\""+(maxYear-1)+"\" AND wt_week>=\""+gWeek+"\") " +
                    "OR (wt_year=\""+maxYear+"\" AND wt_week<=\""+dMaxWeek+"\" AND wt_week>=\""+(dMaxWeek-numberOfWeeks+1)+"\" ) ";
        }

        String selectQuery =  " SELECT * FROM worktime, wage,companies,agencies " +
                " WHERE worktime.wt_comp_id=companies.comp_id " +
                " AND companies.comp_id=wage.wage_comp_id AND worktime.wt_agency_id=agencies.agency_id " +
                whereYear +
                " ORDER BY wt_startdate"
                ;
        String titleLine;
        double hoursOfWeek=0;
        double salaryOfWeek=0;
        int x = 1;

        TableLayout tl = findViewById(R.id.table);

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++) {
            x = x+i;
            if (!fullQuerys_s.get(i).getwt_otwage().equals("0")) {
                rowcolor = R.color.row_overtime;
            } else if (!fullQuerys_s.get(i).get_wt_holiday().equals("0")) {
                rowcolor = R.color.row_holiday;
            } else {
                rowcolor = R.color.toolbar_background;
            }

            if(settingTest("beforevalues").equals("true")){
                titleLine = settingTest("currency")+" "+fullQuerys_s.get(i).getwt_salary()+"\n";
            }else{
                titleLine = fullQuerys_s.get(i).getwt_salary()+" "+settingTest("currency")+"\n";
            }

            hoursOfWeek = hoursOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_hours());
            salaryOfWeek = salaryOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_salary());

            String shift = fullQuerys_s.get(i).getwt_strsdate()+" - "+
                    fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            Date date = new Date((long) fullQuerys_s.get(i).getwt_startdate()*1000L);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE");
            String dayOfTheWeek = sdf.format(date);
            dayOfTheWeek = dayOfTheWeek.replace(" ","");

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.setBackgroundColor(Color.WHITE);
                tr.addView(getTextView(i + 1, dayOfTheWeek+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, rowcolor),0));
                tr.addView(getTextView(i + fullQuerys_s.size(), shift, ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));
                tr.addView(getTextView(i + fullQuerys_s.size(), fullQuerys_s.get(i).getwt_hours()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));
                tr.addView(getTextView(i + fullQuerys_s.size(),titleLine, ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));
            tl.addView(tr, getTblLayoutParams());
        }

        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.WHITE);
        tr.addView(getTextView(1, "", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1, "TOTAL: ", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  ""+hoursOfWeek+"", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  ""+dformat.format(salaryOfWeek)+"", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tl.addView(tr, getTblLayoutParams());
    }


    public Integer maxStartWeek(){
        String selectQuery =  " SELECT max(wt_week) FROM worktime, wage,companies " +
                " WHERE worktime.wt_comp_id=companies.comp_id " +
                " AND companies.comp_id=wage.wage_comp_id"
                ;
        Integer result=0;
        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getMaxStartWeek(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){ result = fullQuerys_s.get(i).getwt_week();
        }
        return result;
    }


    public Integer maxYear(){
        String selectQuery =  " SELECT max(wt_year) FROM worktime";
        Integer result=0;
        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getMaxYear(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){ result = fullQuerys_s.get(i).getwt_year();
        }
        return result;
    }


    public Integer yearValidator(){
        Integer maxYear = maxYear();
        String selectQuery =  " SELECT max(wt_week) FROM worktime WHERE wt_year = "+maxYear;
        Integer maxWeekDepMaxYear=0;
        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getMaxStartWeek(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){ maxWeekDepMaxYear = fullQuerys_s.get(i).getwt_week();
        }
        return maxWeekDepMaxYear;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            setContentView(R.layout.activity_main);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(MainActivity.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(MainActivity.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    public void floatingActionButton(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                 Uj_activity = new Intent(MainActivity.this, Worktimes.class);
                 startActivity(Uj_activity);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }

}