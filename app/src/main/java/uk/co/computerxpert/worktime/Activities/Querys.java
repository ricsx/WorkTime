package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;

public class Querys extends AppCompatActivity implements View.OnClickListener{

    private Spinner _inCegn, _inAgencyName;
    private EditText _inWeekNum, _inStDate, _inEndDate;
    private Button _btnStDate;
    private Button _btnEndDate;
    private CheckBox _inOverTime, _inHoliday;
    Intent Uj_activity;
    private String kezdveg = "k";
    private String chooseCompany, chooseAgency;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    @SuppressLint("SimpleDateFormat")
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querys);

        chooseCompany=getString(R.string.chooseCompany);
        chooseAgency=getString(R.string.chooseAgency);
        _inAgencyName = findViewById(R.id.inAgencyName);
        _inCegn = findViewById(R.id.inCegn);
        _inWeekNum = findViewById(R.id.inWeekNum);
        _inStDate = findViewById(R.id.inStDate);
        _inEndDate = findViewById(R.id.inEndDate);
        _btnStDate = findViewById(R.id.btnStDate);
        _btnEndDate = findViewById(R.id.btnEndDate);
        _inOverTime = findViewById(R.id.inOverTime);
        _inHoliday = findViewById(R.id.inHoliday);
        Button _btn_ViewableFields = findViewById(R.id.btn_ViewableFields);

        App.CompanyListToSpinner(_inCegn, this, "SELECT * FROM Companies", chooseCompany);
        App.AgenciesListToSpinner(_inAgencyName, this, chooseAgency);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        _btnStDate.setOnClickListener(this);
        _btnEndDate.setOnClickListener(this);
        _btn_ViewableFields.setOnClickListener(this);

        _btnStDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateDate();
            }
        });

        _btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "v";
                updateDate();
            }
        });

        _btn_ViewableFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uj_activity = new Intent(Querys.this, ViewableFields.class);
                startActivity(Uj_activity);
            }
        });


    }


    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };


    private void updateTextLabel(){
        if(Objects.equals(kezdveg, "k")) {
                _inStDate.setText(formatDate.format(dateTime.getTime()));
                _btnStDate.setText(formatDate.format(dateTime.getTime()));
        }
        if(Objects.equals(kezdveg, "v")) {
                _inEndDate.setText(formatDate.format(dateTime.getTime()));
                _btnEndDate.setText(formatDate.format(dateTime.getTime()));
        }
    }


    @SuppressLint("SimpleDateFormat")
    public void makeListView (View view) throws ParseException {

        String partQueryCompName, partQueryWeekNum, partQueryStartDate,
                partQueryEndDate, partQueryOvertime, partQueryAgencyName, partQueryHoliday;
        String compName = _inCegn.getSelectedItem().toString();
        String agencyName = _inAgencyName.getSelectedItem().toString();
        String kezddate = _inStDate.getText().toString();
        String vegdate = _inEndDate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String veg_ = vegdate+" 23:59";
        String week = _inWeekNum.getText().toString();
        Integer comp_id;
        Integer agency_id;
        long kezd_uxT=0, veg_uxT=0;

        if(agencyName.equals(chooseAgency)){
            agency_id = 0;
        }else{
             String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + agencyName + "\"";
            agency_id = App.agency_idFromSpinner(selectQuery);
        }


        if(compName.equals(chooseCompany)){
            comp_id=0;
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Companies.TABLE
                    + " WHERE " + Companies.KEY_comp_name
                    + " =\"" + compName + "\"";
            comp_id = App.comp_idFromSpinner(selectQuery);
        }

        // Dates convert to Unix format
        // DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");
        if (kezddate.length() != 0) {
            Date date_kezd = dateFormat.parse(kezd_);
            kezd_uxT = date_kezd.getTime() / 1000;
        }
        if (vegdate.length() != 0) {
            Date date_veg = dateFormat.parse(veg_);
            veg_uxT = date_veg.getTime() / 1000;
        }
        // End of converts
        if (_inOverTime.isChecked() && _inHoliday.isChecked()){
            partQueryOvertime = " AND wt_otwage != 0 "; partQueryHoliday = " OR wt_holiday != 0 ";
        }else {
            if (_inOverTime.isChecked()) { partQueryOvertime = " AND wt_otwage != 0 ";} else { partQueryOvertime = ""; }
            if (_inHoliday.isChecked()) { partQueryHoliday = " AND wt_holiday != 0 ";} else { partQueryHoliday = "";            }
        }
        if (comp_id != 0) { partQueryCompName = " AND wt_comp_id = "+comp_id; }else{  partQueryCompName = ""; }
        if (week.length() != 0) { partQueryWeekNum = " AND wt_week = "+week; }else { partQueryWeekNum = ""; }
        if (kezddate.length() != 0) { partQueryStartDate = " AND wt_startdate > "+kezd_uxT; } else { partQueryStartDate = ""; }
        if (vegdate.length() != 0) { partQueryEndDate = " AND wt_enddate < "+veg_uxT; }else { partQueryEndDate = ""; }
        if (agency_id != 0) { partQueryAgencyName = " AND wt_agency_id = "+agency_id; }else{ partQueryAgencyName = ""; }

        String sel = "select * from worktime,companies,wage,agencies where worktime.wt_comp_id=companies.comp_id and companies.comp_id=wage.wage_comp_id and worktime.wt_agency_id=agencies.agency_id"
                +partQueryCompName+partQueryWeekNum+partQueryStartDate+partQueryEndDate+partQueryOvertime+partQueryAgencyName+partQueryHoliday;

        Uj_activity = new Intent(Querys.this, QuerysResults.class);
        Uj_activity.putExtra("sel", sel);
        startActivity(Uj_activity);

        _inWeekNum.setText("");
        _inStDate.setText("");
        _inEndDate.setText("");
        _inWeekNum.setText("");
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Querys.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Querys.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Querys.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {

    }
}
