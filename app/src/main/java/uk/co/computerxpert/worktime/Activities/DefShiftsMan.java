package uk.co.computerxpert.worktime.Activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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

import uk.co.computerxpert.worktime.App.App;
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
    private String var = "time", kezdveg = "k", notSelected;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_shifts_man);

        notSelected=getString(R.string.NotSelected);
        results=(ListView) findViewById(R.id.result);
        eddefShiftName = (EditText) findViewById(R.id.inp_defShiftName);
        edstarttime = (EditText) findViewById(R.id.inp_startTime);
        edendtime = (EditText) findViewById(R.id.inp_endTime);
        edunpbreak = (EditText) findViewById(R.id.inp_unpBreak);
        spinner = (Spinner) findViewById(R.id.sp_compNames);
        spinnerAgency = (Spinner) findViewById(R.id.sp_agencyNames);

        Button btnstartTime = (Button) findViewById(R.id.btn_startTime);
        Button btnendTime = (Button) findViewById(R.id.btn_endTime);
        Button btnSave = (Button) findViewById(R.id.btn_defShiftsSave);

        App.CompanyListToSpinner(spinner, this, "SELECT * FROM Companies", "false");
        App.AgenciesListToSpinner(spinnerAgency, this, "SELECT * FROM Agencies", notSelected);

        make_listview();
        btnstartTime.setOnClickListener(this);
        btnendTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
            updateTextLabel("time");
        }
    };


    private void updateTextLabel (String var){
        if(kezdveg == "k") { edstarttime.setText(formatTime.format(dateTime.getTime())); }
        if(kezdveg == "v") { edendtime.setText(formatTime.format(dateTime.getTime())); }
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from DefShifts";

        DefShiftsRepo defShiftsRepo = new DefShiftsRepo();
        List<DefShifts> defShifts_s= defShiftsRepo.getDefShifts(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<defShifts_s.size();i++){ values.add(defShifts_s.get(i).get_defsh_name());  }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
        String selectQuery =  query;
        int defShiftsID=0;
        DefShiftsRepo defShiftsRepo = new DefShiftsRepo();
        List<DefShifts> defShifts_s= defShiftsRepo.getDefShifts(selectQuery);

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
        Integer agency_id = null;

        if(agency_name.equals(notSelected)){
            agency_id = null;
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + agency_name + "\"";
            agency_id = App.agency_idFromSpinner(selectQuery);
        }

        String selectQuery = " SELECT * "
                    + " FROM " + Companies.TABLE
                    + " WHERE " + Companies.KEY_comp_name
                    + " =\"" + comp_name + "\"";
        Integer comp_id = App.comp_idFromSpinner(selectQuery);


        DefShifts defShifts = new DefShifts();
        defShifts.set_defsh_name(shiftName);
        defShifts.set_defsh_comp_id(comp_id);
        defShifts.set_defsh_starttime(starttime);
        defShifts.set_defsh_endtime(endtime);
        defShifts.set_defsh_unpbr(Integer.parseInt(unpbreak));
        defShifts.set_defsh_agency_id(agency_id);

        DefShiftsRepo.insert(defShifts);

        eddefShiftName.setText("");
        edstarttime.setText("");
        edendtime.setText("");
        edunpbreak.setText("");
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DefShiftsMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DefShiftsMan.this, Worktimes.class);
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