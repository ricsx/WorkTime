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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_Ertek="TAG: ";
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
        tr.addView(getTextView(0, "SHIFT", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "HOURS", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "WAGE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }


    /**
     * This function add the data to the table
     **/
    public void addData() {

        String selectQuery =  " SELECT * FROM worktime, wage,companies " +
                " WHERE worktime.wt_comp_id=companies.comp_id " +
                " AND companies.comp_id=wage.wage_comp_id "
                ;
        double hoursOfWeek=0;
        double salaryOfWeek=0;

        TableLayout tl = findViewById(R.id.table);

        FullQuerysRepo fullQuerysRepo = new FullQuerysRepo();
        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(selectQuery);
        List<String> values = new ArrayList<String>();

        for(int i=0; i<fullQuerys_s.size();i++){
            values.add(fullQuerys_s.get(i).getcomp_name());
            String aa = fullQuerys_s.get(i).getwt_strsdate();
            values.add(fullQuerys_s.get(i).getwt_strstime());
            values.add(fullQuerys_s.get(i).getwt_stredate());
            values.add(fullQuerys_s.get(i).getwt_stretime());
            values.add(fullQuerys_s.get(i).getwt_hours());
            values.add(fullQuerys_s.get(i).getwt_salary());

            hoursOfWeek = hoursOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_hours());
            salaryOfWeek = salaryOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_salary());

            String shift = fullQuerys_s.get(i).getwt_strsdate()+" - "+fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, shift, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + fullQuerys_s.size(), fullQuerys_s.get(i).getwt_hours()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + fullQuerys_s.size(),"£"+fullQuerys_s.get(i).getwt_salary()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));

            tl.addView(tr, getTblLayoutParams());
        }
        TableRow tr = new TableRow(this);
        tr.addView(getTextView(1, "Sum: ", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+hoursOfWeek, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+salaryOfWeek, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tl.addView(tr, getTblLayoutParams());
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


    public void floatingActionButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Uj_activity = new Intent(MainActivity.this, Querys.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row :: " + id);
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }

}