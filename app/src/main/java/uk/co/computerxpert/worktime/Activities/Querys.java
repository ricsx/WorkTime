package uk.co.computerxpert.worktime.Activities;

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

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;

public class Querys extends AppCompatActivity implements View.OnClickListener{

    private Spinner _inCegn, _inAgencyName;
    private EditText _inWeekNum, _inStDate, _inEndDate;
    private Button _btnStDate, _btnEndDate;
    private CheckBox _inOverTime;
    private Intent Uj_activity;
    private String kezdveg = "k", notSelected, chooseCompany, choosAgency;


    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querys);

        notSelected=getString(R.string.NotSelected);
        chooseCompany=getString(R.string.chooseCompany);
        choosAgency=getString(R.string.chooseAgency);
        _inAgencyName = (Spinner) findViewById(R.id.inAgencyName);
        _inCegn = (Spinner) findViewById(R.id.inCegn);
        _inWeekNum = (EditText) findViewById(R.id.inWeekNum);
        _inStDate = (EditText) findViewById(R.id.inStDate);
        _inEndDate = (EditText) findViewById(R.id.inEndDate);
        _btnStDate = (Button) findViewById(R.id.btnStDate);
        _btnEndDate = (Button) findViewById(R.id.btnEndDate);
        _inOverTime = (CheckBox) findViewById(R.id.inOverTime);

        App.CompanyListToSpinner(_inCegn, this, "SELECT * FROM Companies", chooseCompany);
        App.AgenciesListToSpinner(_inAgencyName, this, "SELECT * FROM Agencies", choosAgency);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        _btnStDate.setOnClickListener(this);
        _btnEndDate.setOnClickListener(this);

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
            updateTextLabel("date");
        }
    };


    private void updateTextLabel (String var){
        if(kezdveg == "k") {
            if (var == "date") {
                _inStDate.setText(formatDate.format(dateTime.getTime()));
                _btnStDate.setText(formatDate.format(dateTime.getTime()));
            }
        }
        if(kezdveg == "v") {
            if (var == "date") {
                _inEndDate.setText(formatDate.format(dateTime.getTime()));
                _btnEndDate.setText(formatDate.format(dateTime.getTime()));
            }
        }
    }


    public void makeListView (View view) throws ParseException {

        String partQueryCompName="", partQueryWeekNum="", partQueryStartDate="",
                partQueryEndDate="", partQueryOvertime="", partQueryAgencyName="";
        String compName = _inCegn.getSelectedItem().toString();
        String agencyName = _inAgencyName.getSelectedItem().toString();
        String kezddate = _inStDate.getText().toString();
        String vegdate = _inEndDate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String veg_ = vegdate+" 23:59";
        String week = _inWeekNum.getText().toString();
        Integer comp_id = 0;
        Integer agency_id = 0;
        long kezd_uxT=0, veg_uxT=0;

        if(agencyName.equals(choosAgency)){
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
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");
        if (kezddate.length() != 0) {
            Date date_kezd = dateFormat.parse(kezd_);
            kezd_uxT = (long) date_kezd.getTime() / 1000;
        }
        if (vegdate.length() != 0) {
            Date date_veg = dateFormat.parse(veg_);
            veg_uxT = (long) date_veg.getTime() / 1000;
        }
        // End of converts
        if (_inOverTime.isChecked()){ partQueryOvertime = " AND wt_otwage != 0 "; }else{ partQueryOvertime = ""; }
        if (comp_id != 0) { partQueryCompName = " AND wt_comp_id = "+comp_id; }else{  partQueryCompName = ""; }
        if (week.length() != 0) { partQueryWeekNum = " AND wt_week = "+week; }else { partQueryWeekNum = ""; }
        if (kezddate.length() != 0) { partQueryStartDate = " AND wt_startdate > "+kezd_uxT; } else { partQueryStartDate = ""; }
        if (vegdate.length() != 0) { partQueryEndDate = " AND wt_enddate < "+veg_uxT; }else { partQueryEndDate = ""; }
        if (agency_id != 0) { partQueryAgencyName = " AND wt_agency_id = "+agency_id; }else{ partQueryAgencyName = ""; }

        String sel = "select * from worktime,companies,wage where worktime.wt_comp_id=companies.comp_id and companies.comp_id=wage.wage_comp_id "
                +partQueryCompName+partQueryWeekNum+partQueryStartDate+partQueryEndDate+partQueryOvertime+partQueryAgencyName;

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
