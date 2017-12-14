package uk.co.computerxpert.worktime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class Dashbrd extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    private EditText in_cegnev;
    private EditText in_kezddate;
    private EditText in_kezdtime;
    private EditText in_vegdate;
    private EditText in_vegtime;

    private int id = 1;
    private Intent Uj_activity;

    private static final String TAG_Ertek="TAG: ";

    private String var = "time", kezdveg = "k";

    Button btn_kezddate, btn_kezdtime, btn_vegdate, btn_vegtime;

    final Calendar dateTime = Calendar.getInstance();
    DateFormat formatDate = DateFormat.getDateInstance();
    DateFormat formatTime = DateFormat.getTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbrd);

        mTextMessage = (TextView) findViewById(R.id.message);
        in_cegnev = (EditText) findViewById(R.id.in_cegnevBox);
        in_kezddate = (EditText) findViewById(R.id.in_kezddateBox);
        in_kezdtime = (EditText) findViewById(R.id.in_kezdtimeBox);
        in_vegdate = (EditText) findViewById(R.id.in_vegdateBox);
        in_vegtime = (EditText) findViewById(R.id.in_vegtimeBox);
        btn_kezddate = (Button) findViewById(R.id.btn_date);
        btn_kezdtime = (Button) findViewById(R.id.btn_kezdtime);
        btn_vegdate = (Button) findViewById(R.id.btn_vegdate);
        btn_vegtime = (Button) findViewById(R.id.btn_vegtime);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        btn_kezddate.setOnClickListener(this);
        btn_kezdtime.setOnClickListener(this);
        btn_vegdate.setOnClickListener(this);
        btn_vegtime.setOnClickListener(this);


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
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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


    public void newWtime (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String cegnev =  in_cegnev.getText().toString();
        String kezddate = in_kezddate.getText().toString();
        String kezdtime = in_kezdtime.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        String vegtime = in_vegtime.getText().toString();
        String kezd = kezddate+"/"+kezdtime;
        String veg = vegdate+"/"+vegtime;

        Wtime wtime =
                new Wtime(1, 1, cegnev, kezd, veg,null);

        Log.i(TAG_Ertek,"wtime "+wtime);

        dbHandler.addWtime(wtime);
        in_cegnev.setText("");
        in_kezddate.setText("");
        in_kezdtime.setText("");
        in_vegdate.setText("");
        in_vegtime.setText("");

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Dashbrd.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Dashbrd.this, Dashbrd.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
