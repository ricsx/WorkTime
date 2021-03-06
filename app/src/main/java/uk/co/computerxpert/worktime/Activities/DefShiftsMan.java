package uk.co.computerxpert.worktime.Activities;

import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Spinner;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import uk.co.computerxpert.worktime.Common.Common;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.DefShifts;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;


public class DefShiftsMan extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    EditText eddefShiftName, edstarttime, edendtime, edunpbreak;
    private ListView results;
    private Spinner spinner, spinnerAgency;
    private String kezdveg = "k";
    private String notSelected;
    private Button btnstartTime, btnendTime;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_shifts_man);

        Common.setStatusBarColor(this.getWindow(), this);

        notSelected=getString(R.string.NotSelected);
        results= findViewById(R.id.result);
        eddefShiftName = findViewById(R.id.inp_defShiftName);
        edstarttime = findViewById(R.id.inp_startTime);
        edendtime = findViewById(R.id.inp_endTime);
        edunpbreak = findViewById(R.id.inp_unpBreak);
        spinner = findViewById(R.id.sp_compNames);
        spinnerAgency = findViewById(R.id.sp_agencyNames);
        btnstartTime = findViewById(R.id.btn_startTime);
        btnendTime = findViewById(R.id.btn_endTime);
        Button btnSave = findViewById(R.id.btn_defShiftsSave);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        String firstRunFlag = getIntent().getStringExtra("firstRunFlag");
        if(firstRunFlag == null){ firstRunFlag ="0"; }

        Toolbar myToolbar = findViewById(R.id.def_shifts_man_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Common.CompanyListToSpinnerAlign(spinner, this, notSelected);
        Common.AgenciesListToSpinnerAlign(spinnerAgency, this, notSelected);

        make_listview();
        btnstartTime.setOnClickListener(this);
        btnendTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        if(firstRunFlag.equals("0")) {
            navigation.setVisibility(View.VISIBLE);
        }else{
            navigation.setVisibility(View.GONE);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        btnstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateTime();

            }
        });

        btnendTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "v";
                updateTime();
            }
        });
    }


    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE), true ).show();
    }


    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel();
        }
    };


    private void updateTextLabel(){
        if(Objects.equals(kezdveg, "k")) {
            edstarttime.setText(formatTime.format(dateTime.getTime()));
            btnstartTime.setText(formatTime.format(dateTime.getTime()));
        }
        if(Objects.equals(kezdveg, "v")) {
            edendtime.setText(formatTime.format(dateTime.getTime()));
            btnendTime.setText(formatTime.format(dateTime.getTime()));
        }
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from DefShifts";
        List<DefShifts> defShifts_s= DefShiftsRepo.getDefShifts(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<defShifts_s.size();i++){ values.add(defShifts_s.get(i).get_defsh_name());  }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        results.setAdapter(adapter);

        // After Clicked...
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemValue = (String) results.getItemAtPosition(position);
                String defshiftsIDString = Integer.toString(getDefShiftsIDFromQuery("SELECT * FROM DefShifts WHERE defsh_name = \""+itemValue+"\""));
                Uj_activity = new Intent(DefShiftsMan.this, DefShiftsManMod.class);
                Uj_activity.putExtra("defShiftsID", defshiftsIDString);
                startActivity(Uj_activity);
            }
        });

    }


    private int getDefShiftsIDFromQuery(String query){
        int defShiftsID=0;
        List<DefShifts> defShifts_s= DefShiftsRepo.getDefShifts(query);

        for(int i=0; i<defShifts_s.size();i++){ defShiftsID = defShifts_s.get(i).get_defsh_id(); }

        return defShiftsID;
    }


    public void defShift_insert(){

        String shiftName =  eddefShiftName.getText().toString();
        String starttime = edstarttime.getText().toString();
        String endtime = edendtime.getText().toString();
        String unpbreak = edunpbreak.getText().toString();
        String comp_name = spinner.getSelectedItem().toString();
        String agency_name = spinnerAgency.getSelectedItem().toString();
        Integer agency_id;

        if(agency_name.equals(notSelected)){
            agency_id = null;
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + agency_name + "\"";
            agency_id = Common.agency_idFromSpinner(selectQuery);
        }

        String selectQuery = " SELECT * "
                    + " FROM " + Companies.TABLE
                    + " WHERE " + Companies.KEY_comp_name
                    + " =\"" + comp_name + "\"";
        Integer comp_id = Common.comp_idFromSpinner(selectQuery);


        DefShifts defShifts = new DefShifts();
        defShifts.set_defsh_name(shiftName);
        defShifts.set_defsh_comp_id(comp_id);
        defShifts.set_defsh_starttime(starttime);
        defShifts.set_defsh_endtime(endtime);
        defShifts.set_defsh_unpbr(Integer.parseInt(unpbreak));
        //noinspection ConstantConditions
        defShifts.set_defsh_agency_id(agency_id);

        DefShiftsRepo.insert(defShifts);

        eddefShiftName.setText("");
        edstarttime.setText("");
        edendtime.setText("");
        edunpbreak.setText("");
        finish();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DefShiftsMan.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DefShiftsMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DefShiftsMan.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_defShiftsSave:
                defShift_insert();
                break;
        }
    }


}