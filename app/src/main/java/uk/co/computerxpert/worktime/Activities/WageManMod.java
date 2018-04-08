package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.computerxpert.worktime.Common.Common;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

import static uk.co.computerxpert.worktime.Common.Common.dateFormat;
import static uk.co.computerxpert.worktime.Common.Common.dateTime;
import static uk.co.computerxpert.worktime.Common.Common.formatDate;
import static uk.co.computerxpert.worktime.Common.Common.makeMonthArray;
import static uk.co.computerxpert.worktime.Common.Common.months;

public class WageManMod extends AppCompatActivity implements View.OnClickListener {

    private EditText in_kezddate, in_val;
    public Spinner spinner1;
    int ed_wage_id, defCompId;
    Button btn_kezddate;
    TextView _tvCompName;
    String fromWageID, defaultCompName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_man_mod);

        Common.setStatusBarColor(this.getWindow(), this);

        _tvCompName = findViewById(R.id.tvCompName);
        spinner1 = findViewById(R.id.spinner2);
        in_kezddate = findViewById(R.id.in_wage_stdateBox2);
        in_val = findViewById(R.id.in_wage_valBox2);
        btn_kezddate = findViewById(R.id.btn_wage_stdate2);
        Button btn_kezddateSt = findViewById(R.id.btn_wage_stdate2st);

        Toolbar myToolbar = findViewById(R.id.wages_man_mod_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        makeMonthArray();

        fromWageID = getIntent().getStringExtra("wageID");
        defaultCompName = getIntent().getStringExtra("compName");
        Button btn_cmmMod = findViewById(R.id.btn_stp_wageSave2);

        loadFormDefaults(fromWageID);
        btn_cmmMod.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        btn_kezddateSt.setOnClickListener(new View.OnClickListener() {
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
            updateTextLabel();
        }
    };


    private void updateTextLabel(){
        in_kezddate.setText(formatDate.format(dateTime.getTime()));
        btn_kezddate.setText(formatDate.format(dateTime.getTime()));
    }

    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Wage,companies WHERE wage.wage_comp_id = companies.comp_id AND " +
                Wage.KEY_wage_id + " = \"" + value + "\"";
        List<Wage> wage_s= WageRepo.relGetWage(selectQuery);
        // "values" array definition and loading
        ed_wage_id = Integer.parseInt(value);
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<wage_s.size();i++){
            _tvCompName.setText(defaultCompName);
            in_kezddate.setText(wage_s.get(i).getwage_strstdate());
            in_val.setText(wage_s.get(i).getwage_val());
            defCompId = wage_s.get(i).getwage_comp_id();
            values.add(wage_s.get(i).getcomp_name());
            btn_kezddate.setText((wage_s.get(i).getwage_strstdate()));
        }
    }


    public void updater() throws ParseException {

        String kezddate = in_kezddate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String val = in_val.getText().toString();

        Date date_kezd = dateFormat.parse(kezd_);
        long kezd_uxT = date_kezd.getTime() /1000;

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
                    Uj_activity = new Intent(WageManMod.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(WageManMod.this, MainActivity.class);
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
