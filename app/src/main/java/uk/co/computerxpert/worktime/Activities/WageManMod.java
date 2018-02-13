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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

import static uk.co.computerxpert.worktime.App.App.compNameFromWageID;
import static uk.co.computerxpert.worktime.App.App.dateFormat;
import static uk.co.computerxpert.worktime.App.App.dateTime;
import static uk.co.computerxpert.worktime.App.App.formatDate;
import static uk.co.computerxpert.worktime.App.App.makeMonthArray;
import static uk.co.computerxpert.worktime.App.App.months;

public class WageManMod extends AppCompatActivity implements View.OnClickListener {

    private EditText in_kezddate, in_val;
    public Spinner spinner1;
    int ed_wage_id, defCompId;
    Button btn_kezddate;
    TextView _tvCompName;
    String fromWageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_man_mod);

        _tvCompName = (TextView) findViewById(R.id.tvCompName);
        spinner1 = (Spinner) findViewById(R.id.spinner2);
        in_kezddate = (EditText) findViewById(R.id.in_wage_stdateBox2);
        in_val = (EditText) findViewById(R.id.in_wage_valBox2);
        btn_kezddate = (Button) findViewById(R.id.btn_wage_stdate2);

        makeMonthArray();

        fromWageID = getIntent().getStringExtra("wageID");
        Button btn_cmmMod = (Button) findViewById(R.id.btn_stp_wageSave2);

        loadFormDefaults(fromWageID);
        btn_cmmMod.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            if (var == "date") {
                in_kezddate.setText(formatDate.format(dateTime.getTime()));
            }
    }

    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Wage,companies WHERE wage.wage_comp_id = companies.comp_id AND " +
                Wage.KEY_wage_id + " = \"" + value + "\"";
        WageRepo wageRepo = new WageRepo();
        List<Wage> wage_s= wageRepo.relGetWage(selectQuery);
        // "values" array definition and loading
        ed_wage_id = Integer.parseInt(value);
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<wage_s.size();i++){
            _tvCompName.setText(compNameFromWageID(ed_wage_id));
            in_kezddate.setText(wage_s.get(i).getwage_strstdate());
            in_val.setText(wage_s.get(i).getwage_val());
            defCompId = wage_s.get(i).getwage_comp_id();
            values.add(wage_s.get(i).getcomp_name());
        }

    }


    public void updater() throws ParseException {

        String kezddate = in_kezddate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String val = in_val.getText().toString();

        Integer wage_id = Integer.valueOf(fromWageID);

        Date date_kezd = dateFormat.parse(kezd_);
        long kezd_uxT = (long)date_kezd.getTime()/1000;

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

        WageRepo.update(Integer.valueOf(fromWageID), defCompId, (double) kezd_uxT, kezddate, val);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent Uj_activity;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(WageManMod.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(WageManMod.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(WageManMod.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stp_wageSave2:
                try {
                    updater();
                    Intent Uj_activity = new Intent(WageManMod.this, WageMan.class);
                    startActivity(Uj_activity);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
