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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;


public class Worktimes extends AppCompatActivity implements View.OnClickListener {

    private EditText in_kezddate, in_kezdtime, in_vegdate, in_vegtime, in_megj, in_unpaidBreak;
    private int id = 1, defAgencyId=0, defCompId=0;
    private Intent Uj_activity;
    private static final String TAG_Ertek = "TAG: ";
    private String var = "time", kezdveg = "k", chooseDefaulShift, notSelected, chooseCompany;
    private Spinner spinnerCompany, spinner2, spinnerAgency;
    private Context context = this;

    Button btn_kezddate, btn_kezdtime, btn_vegdate, btn_vegtime, btn_WorktimeSave;

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worktimes);

        chooseDefaulShift = getString(R.string.ChooseDefaultShift);
        notSelected = getString(R.string.NotSelected);
        chooseCompany = getString(R.string.chooseCompany);
        spinnerCompany = (Spinner) findViewById(R.id.sp_companyNames);
        spinner2 = (Spinner) findViewById(R.id.spinner4);
        spinnerAgency = (Spinner) findViewById(R.id.sp_agencyNames);
        in_kezddate = (EditText) findViewById(R.id.in_kezddateBox);
        in_kezdtime = (EditText) findViewById(R.id.in_kezdtimeBox);
        in_vegdate = (EditText) findViewById(R.id.in_vegdateBox);
        in_vegtime = (EditText) findViewById(R.id.in_vegtimeBox);
        in_megj = (EditText) findViewById(R.id.in_megjBox);
        in_unpaidBreak = (EditText) findViewById(R.id.in_unpaidBreakBox);
        btn_kezddate = (Button) findViewById(R.id.btn_date);
        btn_kezdtime = (Button) findViewById(R.id.btn_kezdtime);
        btn_vegdate = (Button) findViewById(R.id.btn_vegdate);
        btn_vegtime = (Button) findViewById(R.id.btn_vegtime);
        btn_WorktimeSave = (Button) findViewById(R.id.btn_WorktimeSave);

        // starting Spinners
        App.DefShiftsListToSpinner(spinner2, context, "SELECT * FROM DefShifts", chooseDefaulShift);
        loadingDatasFromSpinner2();

        App.AgenciesListToSpinner(spinnerAgency, this, "SELECT * FROM Agencies", notSelected);

        App.CompanyListToSpinner(spinnerCompany, context, "SELECT * FROM Companies", chooseCompany);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_kezddate.setOnClickListener(this);
        btn_kezdtime.setOnClickListener(this);
        btn_vegdate.setOnClickListener(this);
        btn_vegtime.setOnClickListener(this);

        App.makeMonthArray();

        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "k";
                updateDate();
            }
        });

        btn_kezdtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "k";
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


    public void loadingDatasFromSpinner2(){

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                String text = spinner2.getSelectedItem().toString();
                loadFormDefaults(text);
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }
        });
    }


    private void loadFormDefaults(String value) {
        if (value.equals(chooseDefaulShift)) {
            in_kezddate.setText(getString(R.string.DateOfStart));
            in_kezdtime.setText(getString(R.string.TimeOfStart));
            in_vegdate.setText(getString(R.string.DateOfEnd));
            in_vegtime.setText(getString(R.string.TimeOfEnd));
            in_megj.setText("");
            in_unpaidBreak.setText("");
            App.AgenciesListToSpinner(spinnerAgency, this, "SELECT * FROM Agencies", notSelected);
            App.CompanyListToSpinner(spinnerCompany, context, "SELECT * FROM Companies", chooseCompany);

        } else {
            String selectQuery = " SELECT * from DefShifts WHERE " + DefShifts.KEY_DS_Name + " = \"" + value + "\"";
            DefShiftsRepo defShiftsRepo = new DefShiftsRepo();
            List<DefShifts> defShifts_s = defShiftsRepo.getDefShifts(selectQuery);
            // "values" array definition and loading
            ArrayList<String> values = new ArrayList<String>();
            for (int i = 0; i < defShifts_s.size(); i++) {
                in_kezdtime.setText(defShifts_s.get(i).get_defsh_starttime());
                in_vegtime.setText(defShifts_s.get(i).get_defsh_endtime());
                in_unpaidBreak.setText(String.valueOf(defShifts_s.get(i).get_defsh_unpbr()));
                defAgencyId = defShifts_s.get(i).get_defsh_agency_id();
                defCompId = defShifts_s.get(i).get_defsh_comp_id();
                values.add(defShifts_s.get(i).get_defsh_name());
            }
            // Set default to Agency-spinner
            spinnerAgency.setSelection(setPosFromDefAgencyId(defAgencyId));
            spinnerCompany.setSelection(setPosFromDefCompanyId(defCompId));
        }
    }


    private int setPosFromDefAgencyId(int agencyId){
        Integer position=0;
        String selectQuery = " SELECT * FROM Agencies";
        List<Agencies> agencies_s= AgenciesRepo.getAgencies(selectQuery);
        for(int i=0; i<agencies_s.size();i++){
            if(agencies_s.get(i).getagency_id() == agencyId){ position = i+1; }
        } return position;
    }


    private int setPosFromDefCompanyId(int companyId){
        Integer position=0;
        String selectQuery = " SELECT * FROM Companies";
        List<Companies> companies_s = CompaniesRepo.getCompanies(selectQuery);
        for(int i=0; i<companies_s.size();i++){
            if(companies_s.get(i).getcomp_id() == companyId){ position = i+1; }
        } return position;
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
        if(kezdveg.equals("k")) {
            if (var.equals("date")) {
                in_kezddate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var.equals("time")) {
                in_kezdtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
        if(kezdveg.equals("v")) {
            if (var.equals("date")) {
                in_vegdate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var.equals("time")) {
                in_vegtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
    }


    public void newWtime() throws ParseException {
        String cegnev = spinnerCompany.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String kezdtime = in_kezdtime.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        String vegtime = in_vegtime.getText().toString();
        String kezd_ = kezddate+" "+kezdtime;
        String veg_ = vegdate+" "+vegtime;
        String megj =  in_megj.getText().toString();
        String unpbr =  in_unpaidBreak.getText().toString();
        String agency_name = spinnerAgency.getSelectedItem().toString();
        Integer agency_id = null;

        if(agency_name.equals(notSelected)){
            agency_id = 0;
        }else {
            String selectQuery = " SELECT * "
                    + " FROM " + Agencies.TABLE
                    + " WHERE " + Agencies.KEY_agency_name
                    + " =\"" + agency_name + "\"";
            agency_id = App.agency_idFromSpinner(selectQuery);
        }

        // Calculate the comp_id from the spinner return value
        String selectQuery =  " SELECT * "
                + " FROM " + Companies.TABLE
                + " WHERE " + Companies.KEY_comp_name
                + " =\""+ cegnev+"\""
                ;

        Integer comp_id = App.comp_idFromSpinner(selectQuery);

        // Dates convert to Unix format
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy kk:mm", Locale.UK);

        Date date_kezd = dateFormat.parse(kezd_);
        double kezd_uxT = (long)date_kezd.getTime()/1000;

        Date date_veg = dateFormat.parse(veg_);
        double veg_uxT = (long)date_veg.getTime()/1000;
        // End of converts

        // Calculate the correct week-of-year from the selected date
        String bb[] = kezddate.split(" ");
        for(int i=0;i<bb.length;i++){ bb[i] = bb[i].trim(); }

        Calendar now = Calendar.getInstance(Locale.UK);

        int a = Integer.parseInt(bb[2]);
        int c = Integer.parseInt(bb[0]); //.replaceAll(".$", ""));
        int b = App.months.get(bb[1]);

        now.set(Calendar.DATE,c);
        now.set(Calendar.MONTH,b-1);
        now.set(Calendar.YEAR,a);

        int woyear = now.get(Calendar.WEEK_OF_YEAR);
        // End of week-of-year calculate

        // Calculate workhours of day
        float wh = (float) 0.00, wh2 = (float) 0.00;

        if(kezd_uxT>veg_uxT){ wh = (float) ((kezd_uxT - veg_uxT) / 3600); }
        else if(kezd_uxT<veg_uxT){ wh = (float) ((veg_uxT - kezd_uxT) / 3600); }
        if(kezd_uxT>veg_uxT){ wh2 = (float) ((kezd_uxT - veg_uxT - (Integer.parseInt(unpbr)*60)) / 3600); }
        else if(kezd_uxT<veg_uxT){ wh2 = (float) ((veg_uxT - kezd_uxT - (Integer.parseInt(unpbr)*60)) / 3600); }
        String hoursOfDay=Double.toString(Double.parseDouble(decimalFormat.format(wh)));
        String exactHoursOfDay=Double.toString(Double.parseDouble(decimalFormat.format(wh2)));
        // End of workhouse-calculate

        Double wOfDay = App.wageFromWageID(comp_id)*Double.parseDouble(exactHoursOfDay);
        String wageOfDay = Double.toString(Double.parseDouble(decimalFormat.format(wOfDay)));

        uk.co.computerxpert.worktime.data.model.Worktimes worktimes = new uk.co.computerxpert.worktime.data.model.Worktimes();
        worktimes.setwt_comp_id(comp_id);
        worktimes.setwt_startdate(kezd_uxT);
        worktimes.setwt_enddate(veg_uxT);
        worktimes.setwt_rem(megj);
        worktimes.setwt_week(woyear);
        worktimes.setwt_year(a);
        worktimes.setwt_hours(hoursOfDay);
        worktimes.setwt_salary(wageOfDay);
        worktimes.setwt_stredate(kezddate);
        worktimes.setwt_strsdate(vegdate);
        worktimes.setwt_strstime(kezdtime);
        worktimes.setwt_stretime(vegtime);
        worktimes.setwt_unpbr(Integer.parseInt(unpbr));
        worktimes.setwt_agency_id(agency_id);

        // Write datas into DB
        WorktimesRepo.insert(worktimes);

        in_kezddate.setText("");
        in_kezdtime.setText("");
        in_vegdate.setText("");
        in_vegtime.setText("");
        in_megj.setText("");
        in_unpaidBreak.setText("");
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
            case R.id.btn_WorktimeSave:
                try {
                    newWtime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Uj_activity = new Intent(Worktimes.this, MainActivity.class);
                Uj_activity.putExtra("sessid", id);
                startActivity(Uj_activity);
        }
    }
}
