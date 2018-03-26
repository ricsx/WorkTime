package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    private EditText in_kezddate, in_kezdtime, in_vegdate, in_vegtime, in_megj,
            in_unpaidBreak, overTimeWage, holidayWage;
    private int defAgencyId=0, defCompId=0, saveValidator = 0, errorFlag = 0;
    private Intent Uj_activity;
    private String kezdveg = "k", chooseDefaulShift, notSelected, chooseCompany, globalVegdate;
    private Spinner spinnerCompany, spinner2, spinnerAgency;
    private Context context = this;
    Button btn_kezddate, btn_kezdtime, btn_vegdate, btn_vegtime, btn_WorktimeSave, btn_savePayslip;

    private uk.co.computerxpert.worktime.data.model.Worktimes arrWorktimes = new uk.co.computerxpert.worktime.data.model.Worktimes();

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    @SuppressLint("SimpleDateFormat")
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
        spinnerCompany = findViewById(R.id.sp_companyNames);
        spinner2 = findViewById(R.id.spinner4);
        spinnerAgency = findViewById(R.id.sp_agencyNames);
        in_kezddate = findViewById(R.id.in_kezddateBox);
        in_kezdtime = findViewById(R.id.in_kezdtimeBox);
        in_vegdate = findViewById(R.id.in_vegdateBox);
        in_vegtime = findViewById(R.id.in_vegtimeBox);
        in_megj = findViewById(R.id.in_megjBox);
        in_unpaidBreak = findViewById(R.id.in_unpaidBreakBox);
        btn_kezddate = findViewById(R.id.btn_deleteDaysDate);
        btn_kezdtime = findViewById(R.id.btn_kezdtime);
        btn_vegdate = findViewById(R.id.btn_vegdate);
        btn_WorktimeSave = findViewById(R.id.btn_WorktimeSave);
        CheckBox chechBox_overTime = findViewById(R.id.chb_overTime);
        CheckBox checkBox_holiday = findViewById(R.id.chb_holiday);
        btn_savePayslip = findViewById(R.id.btn_savePayslip);
        btn_vegtime = findViewById(R.id.btn_vegtime);
        overTimeWage = findViewById(R.id.overTimeWage);
        holidayWage = findViewById(R.id.holidayWage);
        Toolbar myToolbar = findViewById(R.id.worktimes_top);
        setSupportActionBar(myToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // starting Spinners
        App.DefShiftsListToSpinner(spinner2, context, chooseDefaulShift);
        loadingDatasFromSpinner2();

        App.AgenciesListToSpinner(spinnerAgency, this, notSelected);

        App.CompanyListToSpinner(spinnerCompany, context, "SELECT * FROM Companies", chooseCompany);

        chechBox_overTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    overTimeWage.setVisibility(View.VISIBLE);
                }
                else
                {
                    overTimeWage.setVisibility(View.GONE);
                }
            }
        });

        checkBox_holiday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    holidayWage.setVisibility(View.VISIBLE);
                    in_kezdtime.setText(R.string.nullHour);
                    btn_kezdtime.setText(R.string.nullHour);
                    in_vegtime.setText(R.string.midnight);
                    btn_vegtime.setText(R.string.midnight);
                    in_unpaidBreak.setText("0");
                }
                else
                {
                    holidayWage.setVisibility(View.GONE);
                }
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_kezddate.setOnClickListener(this);
        btn_kezdtime.setOnClickListener(this);
        btn_vegdate.setOnClickListener(this);
        btn_vegtime.setOnClickListener(this);
        btn_savePayslip.setOnClickListener(this);

        App.makeMonthArray();

        btn_savePayslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uj_activity = new Intent(Worktimes.this, SavePayslips.class);
                startActivity(Uj_activity);
            }
        });

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
            App.AgenciesListToSpinner(spinnerAgency, this, notSelected);
            App.CompanyListToSpinner(spinnerCompany, context, "SELECT * FROM Companies", chooseCompany);
        } else {
            String selectQuery = " SELECT * from DefShifts WHERE " + DefShifts.KEY_DS_Name + " = \"" + value + "\"";
            // DefShiftsRepo defShiftsRepo = new DefShiftsRepo();
            List<DefShifts> defShifts_s = DefShiftsRepo.getDefShifts(selectQuery);
            // "values" array definition and loading
            //noinspection MismatchedQueryAndUpdateOfCollection
            ArrayList<String> values = new ArrayList<>();
            for (int i = 0; i < defShifts_s.size(); i++) {
                in_kezdtime.setText(defShifts_s.get(i).get_defsh_starttime());
                btn_kezdtime.setText(defShifts_s.get(i).get_defsh_starttime());
                in_vegtime.setText(defShifts_s.get(i).get_defsh_endtime());
                btn_vegtime.setText(defShifts_s.get(i).get_defsh_endtime());
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
                btn_kezddate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var.equals("time")) {
                in_kezdtime.setText(formatTime.format(dateTime.getTime()));
                 btn_kezdtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
        if(kezdveg.equals("v")) {
            if (var.equals("date")) {
                in_vegdate.setText(formatDate.format(dateTime.getTime()));
                btn_vegdate.setText(formatDate.format(dateTime.getTime()));
            }
            if (var.equals("time")) {
                in_vegtime.setText(formatTime.format(dateTime.getTime()));
                btn_vegtime.setText(formatTime.format(dateTime.getTime()));
            }
        }
    }


    public void newWtime(){
        Double wageOfDay;
        String wt_outwage, wt_holiday;
        String defOvTimeWage = getString(R.string.wageOfTheOtime);
        String defHolidayWage = getString(R.string.wageOfTheHoliday);
        String defUnpaidBreak = getString(R.string.unpaidBreak);
        String cegnev = spinnerCompany.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String kezdtime = in_kezdtime.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        globalVegdate = in_vegdate.getText().toString();
        String vegtime = in_vegtime.getText().toString();
        String kezd_ = kezddate+" "+kezdtime;
        String veg_ = vegdate+" "+vegtime;
        String megj =  in_megj.getText().toString();
        String unpbr =  in_unpaidBreak.getText().toString();
        String agency_name = spinnerAgency.getSelectedItem().toString();
        String ovTimeWage = overTimeWage.getText().toString();
        String holidWage = holidayWage.getText().toString();
        Integer agency_id, wt_unpr;

        if(kezddate.equals("") || kezddate.equals(getString(R.string.DateOfStart)) ||
                kezdtime.equals("") || kezdtime.equals(getString(R.string.TimeOfStart)) ||
                vegdate.equals("") || vegdate.equals(getString(R.string.DateOfEnd)) ||
                vegtime.equals("") || vegtime.equals(getString(R.string.TimeOfEnd))
                )
        {
            errorFlag = 1;
            Toast.makeText(this, getString(R.string.mustFillDateTime),Toast.LENGTH_LONG).show();
        }else {
            errorFlag = 0;
        }

        if(errorFlag != 1) {
            if (unpbr.equals("")) {
                unpbr = "0";
            }
            if (agency_name.equals(notSelected)) {
                agency_id = 0;
            } else {
                String selectQuery = " SELECT * "
                        + " FROM " + Agencies.TABLE
                        + " WHERE " + Agencies.KEY_agency_name
                        + " =\"" + agency_name + "\"";
                agency_id = App.agency_idFromSpinner(selectQuery);
            }

            // Calculate the comp_id from the spinner return value
            String selectQuery = " SELECT * FROM " + Companies.TABLE
                    + " WHERE " + Companies.KEY_comp_name
                    + " =\"" + cegnev + "\"";

            Integer comp_id = App.comp_idFromSpinner(selectQuery);

            // Dates convert to Unix format
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy kk:mm", Locale.UK);

            Date date_kezd = null;
            try {
                date_kezd = dateFormat.parse(kezd_);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double kezd_uxT = 0;
            if (date_kezd != null) {
                kezd_uxT = date_kezd.getTime() / 1000;
            }

            Date date_veg = null;
            try {
                date_veg = dateFormat.parse(veg_);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double veg_uxT = 0;
            if (date_veg != null) {
                veg_uxT = date_veg.getTime() / 1000;
            }
            // End of converts

            // Calculate the correct week-of-year from the selected date
            String bb[] = kezddate.split(" ");
            for (int i = 0; i < bb.length; i++) {
                bb[i] = bb[i].trim();
            }

            Calendar now = Calendar.getInstance(Locale.UK);

            int a = Integer.parseInt(bb[2]);
            int c = Integer.parseInt(bb[0]); //.replaceAll(".$", ""));
            int b = App.months.get(bb[1]);

            now.set(Calendar.DATE, c);
            now.set(Calendar.MONTH, b - 1);
            now.set(Calendar.YEAR, a);

            int woyear = now.get(Calendar.WEEK_OF_YEAR);
            // End of week-of-year calculate

            // Calculate workhours of day
            float wh = (float) 0.00, wh2 = (float) 0.00;

            if (kezd_uxT > veg_uxT) {
                wh = (float) ((kezd_uxT - veg_uxT) / 3600);
            } else if (kezd_uxT < veg_uxT) {
                wh = (float) ((veg_uxT - kezd_uxT) / 3600);
            }
            if (kezd_uxT > veg_uxT) {
                wh2 = (float) ((kezd_uxT - veg_uxT - (Integer.parseInt(unpbr) * 60)) / 3600);
            } else if (kezd_uxT < veg_uxT) {
                wh2 = (float) ((veg_uxT - kezd_uxT - (Integer.parseInt(unpbr) * 60)) / 3600);
            }
            String hoursOfDay = Double.toString(Double.parseDouble(decimalFormat.format(wh)));
            String exactHoursOfDay = Double.toString(Double.parseDouble(decimalFormat.format(wh2)));
            // End of workhouse-calculate

            if (ovTimeWage.equals(defOvTimeWage) || ovTimeWage.equals("")) {
                Double wOfDay = App.wageFromWageID(comp_id) * Double.parseDouble(exactHoursOfDay);
                wageOfDay = Double.parseDouble(decimalFormat.format(wOfDay));
                wt_outwage = "0";
            } else {
                wageOfDay = Double.parseDouble(ovTimeWage) * Double.parseDouble(exactHoursOfDay);
                wageOfDay = Double.parseDouble(decimalFormat.format(wageOfDay));
                wt_outwage = ovTimeWage;
            }

            String wt_salary = String.valueOf(wageOfDay);

            if (unpbr.equals(defUnpaidBreak) || unpbr.equals("")) {
                wt_unpr = 0;
            } else {
                wt_unpr = Integer.parseInt(unpbr);
            }

            if (holidWage.equals(defHolidayWage)||holidWage.equals("")) {
                wt_holiday = "0";
            } else {
                wt_holiday = holidWage;
                wt_salary = holidWage;
                wt_unpr = 0;
                kezdtime = "00:00";
                vegtime = "24:00";
            }

            arrWorktimes.setwt_comp_id(comp_id); // must specified
            arrWorktimes.setwt_startdate(kezd_uxT); // calculated value
            arrWorktimes.setwt_enddate(veg_uxT); // calculated value
            arrWorktimes.setwt_rem(megj); // possible the empty value
            arrWorktimes.setwt_week(woyear); // calculated value
            arrWorktimes.setwt_year(a); // calculated value
            arrWorktimes.setwt_hours(hoursOfDay); // calculated value
            arrWorktimes.setwt_salary(wt_salary); // calculated value
            arrWorktimes.setwt_stredate(kezddate); // must specified
            arrWorktimes.setwt_strsdate(vegdate); // must specified
            arrWorktimes.setwt_strstime(kezdtime); // must specified
            arrWorktimes.setwt_stretime(vegtime); // must specified
            arrWorktimes.setwt_unpbr(wt_unpr); // possible the empty value
            arrWorktimes.setwt_agency_id(agency_id); // possible the empty value
            arrWorktimes.setwt_otwage(wt_outwage); // possible the empty
            arrWorktimes.setwt_holiday(wt_holiday); // possible the empty

            in_kezddate.setText("");
            in_kezdtime.setText("");
            in_vegdate.setText("");
            in_vegtime.setText("");
            in_megj.setText("");
            in_unpaidBreak.setText("");
        }
    }


    private void createDialog(String title, String message) {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

        alertDlg.setTitle(title)
                .setMessage(message)
                .setCancelable(false);

        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveValidator = 1;
                postrunner(arrWorktimes);
            }
        });

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveValidator = 0;
                Uj_activity = new Intent(Worktimes.this, MainActivity.class);
                startActivity(Uj_activity);
            }
        });
        alertDlg.create().show();
    }

    // If say Yes from dialog
    private void postrunner(uk.co.computerxpert.worktime.data.model.Worktimes arrWorktimes){
        //noinspection StatementWithEmptyBody
        if (saveValidator != 1 ) {
        } else {
            WorktimesRepo.insert(arrWorktimes);
            goToHome();
        }
    }

    // date -> data redundancy checking
    public void validator(uk.co.computerxpert.worktime.data.model.Worktimes arrWorktimes, String vegdate){
        String dialogRecordExistWarning = getString(R.string.dialogRecordExistWarning);
        String dialogRecordExistQuestion = getString(R.string.dialogRecordExistQuestion);
        String selectQuery2 =  " SELECT * FROM worktime WHERE worktime.wt_strsdate=\"" + vegdate +"\"";
        List<uk.co.computerxpert.worktime.data.model.Worktimes> worktimes_s = WorktimesRepo.findWorktime(selectQuery2);
        int wts=worktimes_s.size();
        if(wts == 0){
            WorktimesRepo.insert(arrWorktimes);
            if(errorFlag != 1){
                goToHome();
            }
        }
        for(int i=0; i<worktimes_s.size();i++) {
            if (worktimes_s.get(i).getwt_strsdate().equals(vegdate)) {
                createDialog(dialogRecordExistQuestion, dialogRecordExistWarning);
                i=worktimes_s.size();
                if(saveValidator == 1) {
                    WorktimesRepo.insert(arrWorktimes);
                    goToHome();
                }
            }else if(!Objects.equals(worktimes_s.get(i).getwt_strsdate(), vegdate)){
                WorktimesRepo.insert(arrWorktimes);
                goToHome();
            }
        }
    }


    private void goToHome(){
        Uj_activity = new Intent(Worktimes.this, MainActivity.class);
        startActivity(Uj_activity);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Worktimes.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Worktimes.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Worktimes.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_WorktimeSave:
                newWtime();
                validator(arrWorktimes, globalVegdate);
        }
    }
}