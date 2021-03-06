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
import android.widget.Button;
import android.widget.EditText;
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
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;


public class DefShiftsManMod extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    private int defAgencyId, defCompId;
    EditText eddefShiftName, edstarttime, edendtime, edunpbreak;
    private int eddefShiftID = 0;
    private Spinner spinnerCompany, spinnerAgency;
    private String kezdveg = "k", notSelected;
    private Button btnstartTime, btnendTime;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_shifts_man_mod);

        Common.setStatusBarColor(this.getWindow(), this);

        notSelected=getString(R.string.NotSelected);
        eddefShiftName = findViewById(R.id.inp_defShiftName2);
        edstarttime = findViewById(R.id.inp_startTime2);
        edendtime = findViewById(R.id.inp_endTime2);
        edunpbreak = findViewById(R.id.inp_unpBreak2);
        spinnerCompany = findViewById(R.id.sp_compNames2);
        spinnerAgency = findViewById(R.id.sp_agencyNames2);

        Toolbar myToolbar = findViewById(R.id.def_shifts_man_mod_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        btnstartTime = findViewById(R.id.btn_startTime2);
        btnendTime = findViewById(R.id.btn_endTime2);
        Button btnstartTimest = findViewById(R.id.btn_startTime2St);
        Button btnendTimest = findViewById(R.id.btn_endTime2st);
        Button btnSave = findViewById(R.id.btn_defShiftsSave2);

        String fromDefShiftsID = getIntent().getStringExtra("defShiftsID");

        Common.CompanyListToSpinnerAlign(spinnerCompany, this, "false");
        Common.AgenciesListToSpinnerAlign(spinnerAgency, this, notSelected);

        loadFormDefaults(fromDefShiftsID);


        btnstartTime.setOnClickListener(this);
        btnstartTimest.setOnClickListener(this);
        btnendTime.setOnClickListener(this);
        btnendTimest.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        btnstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateTime();
            }
        });

        btnstartTimest.setOnClickListener(new View.OnClickListener() {
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

        btnendTimest.setOnClickListener(new View.OnClickListener() {
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
        List<DefShifts> defShifts_s= DefShiftsRepo.getDefShifts(selectQuery);
        // "values" array definition and loading
        eddefShiftID = Integer.parseInt(value);
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<defShifts_s.size();i++){
            eddefShiftName.setText(defShifts_s.get(i).get_defsh_name());
            edstarttime.setText(defShifts_s.get(i).get_defsh_starttime());
            btnstartTime.setText(defShifts_s.get(i).get_defsh_starttime());
            edendtime.setText(defShifts_s.get(i).get_defsh_endtime());
            btnendTime.setText(defShifts_s.get(i).get_defsh_endtime());
            edunpbreak.setText(String.valueOf(defShifts_s.get(i).get_defsh_unpbr()));
            defCompId = defShifts_s.get(i).get_defsh_comp_id();
            defAgencyId = defShifts_s.get(i).get_defsh_agency_id();
            values.add(defShifts_s.get(i).get_defsh_name());
        }
        if(defAgencyId==0) {
            Common.AgenciesListToSpinnerAlign(spinnerAgency, this, notSelected);
        }else{
            spinnerAgency.setSelection(setPosFromDefAgencyId(defAgencyId)+1);
        }
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
        String selectQuery = " SELECT * FROM Agencies WHERE agency_id!=0";
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


    private void updater(){
        String  _eddefShiftName= eddefShiftName.getText().toString();
        String _edstarttime = edstarttime.getText().toString();
        String _edendtime = edendtime.getText().toString();
        String _edunbreak = edunpbreak.getText().toString();
        String _comp_name = spinnerCompany.getSelectedItem().toString();
        String _agency_name = spinnerAgency.getSelectedItem().toString();
        Integer _agency_id;

        if(_agency_name.equals(notSelected)){
            _agency_id = 0;
            AgenciesRepo.insertEmptyAgency();
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + _agency_name + "\"";
            _agency_id = Common.agency_idFromSpinner(selectQuery);
        }

        String selectQuery =  " SELECT * "
                + " FROM " + Companies.TABLE
                + " WHERE " + Companies.KEY_comp_name
                + " =\""+ _comp_name+"\""
                ;
        Integer _comp_id = Common.comp_idFromSpinner(selectQuery);

        DefShiftsRepo.update(eddefShiftID, _eddefShiftName, _comp_id, _edstarttime,
                _edendtime, Integer.parseInt(_edunbreak), _agency_id);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DefShiftsManMod.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DefShiftsManMod.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DefShiftsManMod.this, Setup.class);
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