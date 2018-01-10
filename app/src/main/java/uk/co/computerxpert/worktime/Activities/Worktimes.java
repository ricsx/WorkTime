package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;

public class Worktimes extends AppCompatActivity implements View.OnClickListener {

    private EditText in_kezddate;
    private EditText in_kezdtime;
    private EditText in_vegdate;
    private EditText in_vegtime;
    private EditText in_megj;
    private int id = 1;
    private Intent Uj_activity;
    private static final String TAG_Ertek="TAG: ";
    private String var = "time", kezdveg = "k";
    private Spinner spinner1;
    private ListView result;
    private Context context = this;

    Button btn_kezddate, btn_kezdtime, btn_vegdate, btn_vegtime;

    private Map<String, Integer> months = new HashMap<String, Integer>();

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worktimes);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        in_kezddate = (EditText) findViewById(R.id.in_kezddateBox);
        in_kezdtime = (EditText) findViewById(R.id.in_kezdtimeBox);
        in_vegdate = (EditText) findViewById(R.id.in_vegdateBox);
        in_vegtime = (EditText) findViewById(R.id.in_vegtimeBox);
        in_megj = (EditText) findViewById(R.id.in_megjBox);
        btn_kezddate = (Button) findViewById(R.id.btn_date);
        btn_kezdtime = (Button) findViewById(R.id.btn_kezdtime);
        btn_vegdate = (Button) findViewById(R.id.btn_vegdate);
        btn_vegtime = (Button) findViewById(R.id.btn_vegtime);

        // starting Spinner (Company names)
        String selectQuery =  "SELECT * FROM Companies";

        App.CompanyListToSpinner(spinner1, context, selectQuery);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_kezddate.setOnClickListener(this);
        btn_kezdtime.setOnClickListener(this);
        btn_vegdate.setOnClickListener(this);
        btn_vegtime.setOnClickListener(this);

        months.put("Jan",1); months.put("Feb",2); months.put("Mar",3); months.put("Apr",4); months.put("May",5);
        months.put("Jun",6); months.put("Jul",7); months.put("Aug",8); months.put("Sep",9); months.put("Oct",10);
        months.put("Nov",11); months.put("Dec",12);

        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateDate();
            }
        });

        btn_kezdtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateTime();
            }
        });

        btn_vegdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "v";
                updateDate();
            }
        });

        btn_vegtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "v";
                updateTime();
            }
        });

    }


    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE), true ).show();
    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year,  int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel("date");
        }
    };


    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel("time");
        }
    };


    private void updateTextLabel (String var){
        if(kezdveg == "k") {
            if (var == "date") {
                in_kezddate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var == "time") {
                in_kezdtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
        if(kezdveg == "v") {
            if (var == "date") {
                in_vegdate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var == "time") {
                in_vegtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
    }


    public void newWtime (View view) throws ParseException {
        // MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String cegnev = spinner1.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String kezdtime = in_kezdtime.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        String vegtime = in_vegtime.getText().toString();
        String kezd_ = kezddate+" "+kezdtime;
        String veg_ = vegdate+" "+vegtime;
        String megj =  in_megj.getText().toString();

        // Calculate the comp_id from the spinner return value
        String selectQuery =  " SELECT Companies." + Companies.KEY_comp_id
                + ", Companies." + Companies.KEY_comp_name
                + " FROM " + Companies.TABLE
                + " WHERE " + Companies.KEY_comp_name
                + " =\""+ cegnev+"\""
                ;

        Integer comp_id = App.comp_idFromSpinner(selectQuery);

        // Dates convert to Unix format
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");

        Date date_kezd = dateFormat.parse(kezd_);
        long kezd_uxT = (long)date_kezd.getTime()/1000;

        Date date_veg = dateFormat.parse(veg_);
        long veg_uxT = (long)date_veg.getTime()/1000;
        // End of converts

        // Calculate the correct week-of-year from the selected date
        String bb[] = kezddate.split(" ");
        for(int i=0;i<bb.length;i++){ bb[i] = bb[i].trim(); }

        Calendar now = Calendar.getInstance(Locale.UK);

        int a = Integer.parseInt(bb[2]);
        int c = Integer.parseInt(bb[0]); //.replaceAll(".$", ""));
        int b = months.get(bb[1]);

        now.set(Calendar.DATE,c);
        now.set(Calendar.MONTH,b-1);
        now.set(Calendar.YEAR,a);

        int woyear = now.get(Calendar.WEEK_OF_YEAR);
        // End of week-of-year calculate

        uk.co.computerxpert.worktime.data.model.Worktimes worktimes = new uk.co.computerxpert.worktime.data.model.Worktimes();
        worktimes.setwt_comp_id(comp_id);
        // worktimes.setwt_compnm(cegnev);
        worktimes.setwt_startdate(kezd_uxT);
        worktimes.setwt_enddate(veg_uxT);
        worktimes.setwt_rem(megj);
        worktimes.setwt_week(woyear);
        worktimes.setwt_year(a);

        // Write datas into DB
        WorktimesRepo.insert(worktimes);

        in_kezddate.setText("");
        in_kezdtime.setText("");
        in_vegdate.setText("");
        in_vegtime.setText("");
        in_megj.setText("");

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Worktimes.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Worktimes.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Worktimes.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_date:
                Log.i(TAG_Ertek, "date");
                break;
            case R.id.btn_kezdtime:
                Log.i(TAG_Ertek, "kezdtm");
                break;
            case R.id.btn_vegtime:
                Log.i(TAG_Ertek, "vegtm");
                break;
        }
    }
}
