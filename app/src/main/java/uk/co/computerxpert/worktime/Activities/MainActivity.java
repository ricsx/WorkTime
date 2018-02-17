package uk.co.computerxpert.worktime.Activities;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;

import static uk.co.computerxpert.worktime.App.App.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Intent Uj_activity;
    int id = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addHeaders();
        addData();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        floatingActionButton();

    }



    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setTextSize(10);
        tv.setOnClickListener(this);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
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
        tr.addView(getTextView(0, "DAY", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(1, "SHIFT", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(2, "HOURS", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(3, "WAGE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }


    /**
     * This function add the data to the table
     **/
    public void addData() {

        Integer rowcolor;
        double maxStartDate = maxStartDate();
        double minMaxStartDate = maxStartDate - OneDayUxt*8;

        String selectQuery =  " SELECT * FROM worktime, wage,companies " +
                " WHERE worktime.wt_comp_id=companies.comp_id " +
                " AND companies.comp_id=wage.wage_comp_id " +
                "AND wt_startdate <= " + maxStartDate + " AND wt_startdate >= " + minMaxStartDate +
                " ORDER BY wt_startdate"
                ;
        double hoursOfWeek=0;
        double salaryOfWeek=0;

        TableLayout tl = findViewById(R.id.table);

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++) {

            if (fullQuerys_s.get(i).getwt_otwage().equals("0")) {
                rowcolor = R.color.row_normal;
            } else {
                rowcolor = R.color.row_overtime;
            }

            hoursOfWeek = hoursOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_hours());
            salaryOfWeek = salaryOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_salary());

            String shift = fullQuerys_s.get(i).getwt_strsdate()+" - "+
                    fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            Date date = new Date((long) fullQuerys_s.get(i).getwt_startdate()*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String dayOfTheWeek = sdf.format(date);

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, dayOfTheWeek+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            tr.addView(getTextView(i + fullQuerys_s.size(), shift, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            tr.addView(getTextView(i + fullQuerys_s.size(), fullQuerys_s.get(i).getwt_hours()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            tr.addView(getTextView(i + fullQuerys_s.size(),"£"+fullQuerys_s.get(i).getwt_salary()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            // tr.addView(getTextView(i + fullQuerys_s.size(),tmp2+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));

            tl.addView(tr, getTblLayoutParams());
        }

        TableRow tr = new TableRow(this);
        tr.addView(getTextView(1, "", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1, "Sum: ", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+hoursOfWeek, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+dformat.format(salaryOfWeek), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tl.addView(tr, getTblLayoutParams());
    }


    public double maxStartDate(){
        String selectQuery =  " SELECT max(wt_startdate) FROM worktime, wage,companies " +
                " WHERE worktime.wt_comp_id=companies.comp_id " +
                " AND companies.comp_id=wage.wage_comp_id"
                ;
        double aa=0.00;
        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getMaxStartDate(selectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){ aa = fullQuerys_s.get(i).getwt_startdate();
        }

        return aa;
    }


    public String uxdateToString(long uxdate){

        Date date = new Date(uxdate*1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm", Locale.UK);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            setContentView(R.layout.activity_main);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(MainActivity.this, uk.co.computerxpert.worktime.Activities.Worktimes.class);
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Uj_activity = new Intent(MainActivity.this, Querys.class);
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