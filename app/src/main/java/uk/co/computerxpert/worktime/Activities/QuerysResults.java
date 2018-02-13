package uk.co.computerxpert.worktime.Activities;

/**
 * Created by ricsx on 10/01/18.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;

import static uk.co.computerxpert.worktime.App.App.dformat;


public class QuerysResults extends AppCompatActivity implements View.OnClickListener {

    private static Intent Uj_activity;
    private String newSelectQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querys_results);
        newSelectQuery = getIntent().getStringExtra("sel");

        addHeaders();
        addData();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
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


    public void addData() {

        double hoursOfWeek=0;
        double salaryOfWeek=0;
        Integer rowcolor;

        TableLayout tl = findViewById(R.id.table);

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(newSelectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){

            hoursOfWeek = hoursOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_hours());
            salaryOfWeek = salaryOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_salary());
            if (fullQuerys_s.get(i).getwt_otwage().equals("0")) {
                rowcolor = R.color.row_normal;
            } else {
                rowcolor = R.color.row_overtime;
            }

            String shift = fullQuerys_s.get(i).getcomp_name()+" \n"+
                    fullQuerys_s.get(i).getwt_strsdate()+" - "+fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, shift, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            tr.addView(getTextView(i + fullQuerys_s.size(), "\n"+fullQuerys_s.get(i).getwt_hours()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));
            tr.addView(getTextView(i + fullQuerys_s.size(),"\n£"+fullQuerys_s.get(i).getwt_salary()+"\n", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, rowcolor)));

            tl.addView(tr, getTblLayoutParams());
        }
        TableRow tr = new TableRow(this);
        tr.addView(getTextView(1, "SUM: ", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+hoursOfWeek, Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(1,  ""+dformat.format(salaryOfWeek), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
        tl.addView(tr, getTblLayoutParams());
    }


    public String uxdateToString(long uxdate){

        Date date = new Date(uxdate*1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm", Locale.UK);
        String formattedDate = sdf.format(date);
        return formattedDate;
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(QuerysResults.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(QuerysResults.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(QuerysResults.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

}