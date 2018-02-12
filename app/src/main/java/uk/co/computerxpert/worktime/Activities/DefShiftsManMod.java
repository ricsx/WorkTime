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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.DecimalFormat;
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
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;


public class DefShiftsManMod extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    private int id=1, defAgencyId, defCompId;
    private static final String TAG_Ertek="TAG: ";
    EditText eddefShiftName, edstarttime, edendtime, edunpbreak;
    private int eddefShiftID = 0;
    private Spinner spinnerCompany, spinnerAgency;
    private String var = "time", kezdveg = "k", notSelected;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_shifts_man_mod);

        notSelected=getString(R.string.NotSelected);
        eddefShiftName = (EditText) findViewById(R.id.inp_defShiftName2);
        edstarttime = (EditText) findViewById(R.id.inp_startTime2);
        edendtime = (EditText) findViewById(R.id.inp_endTime2);
        edunpbreak = (EditText) findViewById(R.id.inp_unpBreak2);
        spinnerCompany = (Spinner) findViewById(R.id.sp_compNames2);
        spinnerAgency = (Spinner) findViewById(R.id.sp_agencyNames2);

        Button btnstartTime = (Button) findViewById(R.id.btn_startTime2);
        Button btnendTime = (Button) findViewById(R.id.btn_endTime2);
        Button btnSave = (Button) findViewById(R.id.btn_defShiftsSave2);

        String fromDefShiftsID = getIntent().getStringExtra("defShiftsID");

        App.CompanyListToSpinner(spinnerCompany, this,"SELECT * FROM Companies" , "false");
        App.AgenciesListToSpinner(spinnerAgency, this, "SELECT * FROM Agencies", "false");

        loadFormDefaults(fromDefShiftsID);


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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updater();
                Uj_activity = new Intent(DefShiftsManMod.this, DefShiftsMan.class);
                startActivity(Uj_activity);
            }
        });

    }


    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from DefShifts WHERE " + DefShifts.KEY_DS_ID + " = \"" + value + "\"";
        DefShiftsRepo defShiftsRepo = new DefShiftsRepo();
        List<DefShifts> defShifts_s= defShiftsRepo.getDefShifts(selectQuery);
        // "values" array definition and loading
        eddefShiftID = Integer.parseInt(value);
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<defShifts_s.size();i++){
            eddefShiftName.setText(defShifts_s.get(i).get_defsh_name());
            edstarttime.setText(defShifts_s.get(i).get_defsh_starttime());
            edendtime.setText(defShifts_s.get(i).get_defsh_endtime());
            edunpbreak.setText(String.valueOf(defShifts_s.get(i).get_defsh_unpbr()));
            defCompId = defShifts_s.get(i).get_defsh_comp_id();
            defAgencyId = defShifts_s.get(i).get_defsh_agency_id();
            values.add(defShifts_s.get(i).get_defsh_name());
        }
        spinnerAgency.setSelection(setPosFromDefAgencyId(defAgencyId));
        spinnerCompany.setSelection(setPosFromDefCompanyId(defCompId));
    }

    private int setPosFromDefCompanyId(int companyId){
        Integer position=0;
        String selectQuery = " SELECT * FROM Companies";
        List<Companies> companies_s = CompaniesRepo.getCompanies(selectQuery);
        for(int i=0; i<companies_s.size();i++){
            if(companies_s.get(i).getcomp_id() == companyId){ position = i; }
        } return position;
    }


    private int setPosFromDefAgencyId(int agencyId){
        Integer position=0;
        String selectQuery = " SELECT * FROM Agencies";
        List<Agencies> agencies_s= AgenciesRepo.getAgencies(selectQuery);
        for(int i=0; i<agencies_s.size();i++){
            if(agencies_s.get(i).getagency_id() == agencyId){ position = i; }
        } return position;
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


    private void updater(){
        String  _eddefShiftName= eddefShiftName.getText().toString();
        String _edstarttime = edstarttime.getText().toString();
        String _edendtime = edendtime.getText().toString();
        String _edunbreak = edunpbreak.getText().toString();
        String _comp_name = spinnerCompany.getSelectedItem().toString();
        String _agency_name = spinnerAgency.getSelectedItem().toString();
        Integer _agency_id = 0;

        if(_agency_name.equals(notSelected)){
            _agency_id = 0;
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + _agency_name + "\"";
            _agency_id = App.agency_idFromSpinner(selectQuery);
        }

        String selectQuery =  " SELECT * "
                + " FROM " + Companies.TABLE
                + " WHERE " + Companies.KEY_comp_name
                + " =\""+ _comp_name+"\""
                ;
        Integer _comp_id = App.comp_idFromSpinner(selectQuery);

        DefShiftsRepo.update(eddefShiftID, _eddefShiftName, _comp_id, _edstarttime,
                _edendtime, Integer.parseInt(_edunbreak), _agency_id);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DefShiftsManMod.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DefShiftsManMod.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DefShiftsManMod.this, Setup.class);
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
            case R.id.btn_defShiftsSave:
                updater();
                break;
        }
    }


}