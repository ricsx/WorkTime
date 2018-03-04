package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;

import static uk.co.computerxpert.worktime.App.App.dformat;


public class QuerysResults extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;
    private String newSelectQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querys_results);
        newSelectQuery = getIntent().getStringExtra("sel");
        Toolbar myToolbar = findViewById(R.id.querys_result_top);

        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        addHeaders();
        addData();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(6, 6, 0, 0);
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
        tr.setBackgroundColor(Color.WHITE);
        tr.addView(getTextView(0, "", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16));
        tr.addView(getTextView(1, "SHIFT", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16));
        tr.addView(getTextView(2, "HOURS", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16 ));
        tr.addView(getTextView(3, "WAGE", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16 ));

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
                rowcolor = R.color.toolbar_background;
            } else {
                rowcolor = R.color.row_overtime;
            }

            String shift = fullQuerys_s.get(i).getcomp_name()+" \n"+
                    fullQuerys_s.get(i).getwt_strsdate()+" - "+fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            Date date = new Date((long) fullQuerys_s.get(i).getwt_startdate()*1000L);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE");
            String dayOfTheWeek = sdf.format(date);
            dayOfTheWeek = dayOfTheWeek.replace(" ","");
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.setBackgroundColor(Color.WHITE);
            tr.addView(getTextView(i + 1, "\n"+dayOfTheWeek+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, rowcolor),0));
            tr.addView(getTextView(i + 1, shift, ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));
            tr.addView(getTextView(i + fullQuerys_s.size(), "\n"+fullQuerys_s.get(i).getwt_hours()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));
            tr.addView(getTextView(i + fullQuerys_s.size(),"\n£"+fullQuerys_s.get(i).getwt_salary()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0));

            tl.addView(tr, getTblLayoutParams());
        }
        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.WHITE);
        tr.addView(getTextView(1, "", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1, "TOTAL: ", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  ""+hoursOfWeek, ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  ""+dformat.format(salaryOfWeek), ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tl.addView(tr, getTblLayoutParams());
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