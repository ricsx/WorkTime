package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;

import static uk.co.computerxpert.worktime.App.App.settingTest;

public class Setup extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    private EditText ed_currency;
    Switch _sw_beforeValue;
    String statusSwitch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button companies = findViewById(R.id.btn_companies2);
        Button wages = findViewById(R.id.btn_wages2);
        Button dev = findViewById(R.id.btn_dev);
        Button agencies = findViewById(R.id.btn_agencies2);
        Button defShifts = findViewById(R.id.btn_defShifts2);
        Button btn_saveCurrency = findViewById(R.id.btn_saveCurrency);
        Button _deleteDays = findViewById(R.id.btn_deletedays);
        ed_currency = findViewById(R.id.ed_currency);
        _sw_beforeValue = findViewById(R.id.sw_dayName);

        loadSwitchDefaults(_sw_beforeValue);
        _sw_beforeValue.setTextOn("true");
        _sw_beforeValue.setTextOff("false");

        loadDefaults();

        companies.setOnClickListener(this);
        wages.setOnClickListener(this);
        dev.setOnClickListener(this);
        agencies.setOnClickListener(this);
        defShifts.setOnClickListener(this);
        btn_saveCurrency.setOnClickListener(this);
        _deleteDays.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void loadDefaults(){
        String selectQuery = " SELECT * from Settings WHERE " + Settings.KEY_settings_name + " = \"currency\"";
        List<Settings> settings_s = SettingsRepo.getSettings(selectQuery);
        //noinspection MismatchedQueryAndUpdateOfCollection
        // ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < settings_s.size(); i++) ed_currency.setText(settings_s.get(i).get_settings_val());
    }

    public void loadSwitchDefaults(Switch switchName){
        String selectQuery = " SELECT * from Settings WHERE " + Settings.KEY_settings_name + " = \""+ "beforevalues" +"\"";
        List<Settings> settings_s = SettingsRepo.getSettings(selectQuery);
        for (int i = 0; i < settings_s.size(); i++) switchName.setChecked(Boolean.parseBoolean(settings_s.get(i).get_settings_val()));
    }


    public void saveCurrency() {

        String currency_val = ed_currency.getText().toString();
        Settings settings = new Settings();
        settings.set_settings_name("currency");
        settings.set_settings_val(currency_val);
        Settings beforeVal = new Settings();
        beforeVal.set_settings_name("beforevalues");
        beforeVal.set_settings_val(statusSwitch1);

        if (settingTest("currency").equals("")) {
            SettingsRepo.insert(settings);
        } else {
            SettingsRepo.update("currency", currency_val);
        }
        if (settingTest("beforevalues").equals("")) {
            SettingsRepo.insert(beforeVal);
        } else {
            SettingsRepo.update("beforevalues", statusSwitch1);
        }
    }


    public void onClick(final android.view.View v){
        switch (v.getId()) {
            case R.id.btn_companies2:
                Uj_activity = new Intent(Setup.this, CompaniesMan.class);
                Uj_activity.putExtra("firstRunFlag", "0");
                startActivity(Uj_activity);
                break;
            case R.id.btn_wages2:
                Uj_activity = new Intent(Setup.this, WageMan.class);
                Uj_activity.putExtra("firstRunFlag", "0");
                startActivity(Uj_activity);
                break;
            case R.id.btn_dev:
                Uj_activity = new Intent(Setup.this, DeveloperSection.class);
                Uj_activity.putExtra("firstRunFlag", "0");
                startActivity(Uj_activity);
                break;
            case R.id.btn_agencies2:
                Uj_activity = new Intent(Setup.this, AgenciesMan.class);
                Uj_activity.putExtra("firstRunFlag", "0");
                startActivity(Uj_activity);
                break;
            case R.id.btn_defShifts2:
                Uj_activity = new Intent(Setup.this, DefShiftsMan.class);
                Uj_activity.putExtra("firstRunFlag", "0");
                startActivity(Uj_activity);
                break;
           case R.id.btn_saveCurrency:
                if (_sw_beforeValue.isChecked()) statusSwitch1 = _sw_beforeValue.getTextOn().toString();
                else statusSwitch1 = _sw_beforeValue.getTextOff().toString();
                saveCurrency();
                Uj_activity = new Intent(Setup.this, Setup.class);
                startActivity(Uj_activity);
                break;
            case R.id.btn_deletedays:
                Uj_activity = new Intent(Setup.this, DeleteWrongDays.class);
                startActivity(Uj_activity);
                break;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Setup.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Setup.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Setup.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

}
