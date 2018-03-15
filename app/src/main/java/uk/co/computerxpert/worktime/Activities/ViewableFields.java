package uk.co.computerxpert.worktime.Activities;

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
import android.widget.Switch;

import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;

import static uk.co.computerxpert.worktime.App.App.settingTest;

public class ViewableFields extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;
    Button _btn_saveVblFields;
    Switch _sw_dayName, _sw_shift, _sw_paidHours, _sw_wage, _sw_unpBreak,
        _sw_agencies, _sw_companies, _sw_comments;
    String dayNameSwitch, shiftSwitch, paidHoursSwitch, wageSwitch, unpBreakSwitch,
        commentsSwitch, companiesSwitch, agenciesSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewable_fields);

        Toolbar myToolbar = findViewById(R.id.ViewableFields_top);

        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        _sw_dayName = findViewById(R.id.sw_dayName);
        _sw_shift = findViewById(R.id.sw_shift);
        _sw_paidHours = findViewById(R.id.sw_paidHours);
        _sw_wage = findViewById(R.id.sw_wage);
        _sw_unpBreak = findViewById(R.id.sw_unpBreak);
        _sw_comments = findViewById(R.id.sw_comments);
        _sw_companies = findViewById(R.id.sw_companies);
        _sw_agencies = findViewById(R.id.sw_agencies);
        _btn_saveVblFields = findViewById(R.id.btn_saveVblFields);

        loadSwitchDefaults(_sw_dayName, "view_dayNameValues");
        _sw_dayName.setTextOn("true");  _sw_dayName.setTextOff("false");
        loadSwitchDefaults(_sw_shift, "view_shiftValues");
        _sw_shift.setTextOn("true");  _sw_shift.setTextOff("false");
        loadSwitchDefaults(_sw_paidHours, "view_paidHoursValues");
        _sw_paidHours.setTextOn("true");  _sw_paidHours.setTextOff("false");
        loadSwitchDefaults(_sw_wage, "view_wageValues");
        _sw_wage.setTextOn("true");  _sw_wage.setTextOff("false");
        loadSwitchDefaults(_sw_unpBreak, "view_unpBreakValues");
        _sw_unpBreak.setTextOn("true");  _sw_unpBreak.setTextOff("false");
        loadSwitchDefaults(_sw_comments, "view_commentsValues");
        _sw_comments.setTextOn("true");  _sw_comments.setTextOff("false");
        loadSwitchDefaults(_sw_companies, "view_companiesValues");
        _sw_companies.setTextOn("true");  _sw_companies.setTextOff("false");
        loadSwitchDefaults(_sw_agencies, "view_agenciesValues");
        _sw_agencies.setTextOn("true");  _sw_agencies.setTextOff("false");

        _btn_saveVblFields.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void loadSwitchDefaults(Switch switchName, String value){
        String selectQuery = " SELECT * from Settings WHERE " + Settings.KEY_settings_name + " = \""+value+"\"";
        List<Settings> settings_s = SettingsRepo.getSettings(selectQuery);
        for (int i = 0; i < settings_s.size(); i++) switchName.setChecked(Boolean.parseBoolean(settings_s.get(i).get_settings_val()));
    }


    public void saveSwitches() {
        Settings dayNameVal = new Settings();
        dayNameVal.set_settings_name("view_dayNameValues");
        dayNameVal.set_settings_val(dayNameSwitch);

        if (settingTest("view_dayNameValues").equals("")) {
            SettingsRepo.insert(dayNameVal);
        } else {
            SettingsRepo.update("view_dayNameValues", dayNameSwitch);
        }

        Settings shiftVal = new Settings();
        shiftVal.set_settings_name("view_shiftValues");
        shiftVal.set_settings_val(shiftSwitch);

        if (settingTest("view_shiftValues").equals("")) {
            SettingsRepo.insert(shiftVal);
        } else {
            SettingsRepo.update("view_shiftValues", shiftSwitch);
        }

        Settings paidHoursVal = new Settings();
        paidHoursVal.set_settings_name("view_paidHoursValues");
        paidHoursVal.set_settings_val(paidHoursSwitch);

        if (settingTest("view_paidHoursValues").equals("")) {
            SettingsRepo.insert(paidHoursVal);
        } else {
            SettingsRepo.update("view_paidHoursValues", paidHoursSwitch);
        }

        Settings wageVal = new Settings();
        wageVal.set_settings_name("view_wageValues");
        wageVal.set_settings_val(wageSwitch);

        if (settingTest("view_wageValues").equals("")) {
            SettingsRepo.insert(wageVal);
        } else {
            SettingsRepo.update("view_wageValues", wageSwitch);
        }

        Settings unpBreakVal = new Settings();
        unpBreakVal.set_settings_name("view_unpBreakValues");
        unpBreakVal.set_settings_val(unpBreakSwitch);

        if (settingTest("view_unpBreakValues").equals("")) {
            SettingsRepo.insert(unpBreakVal);
        } else {
            SettingsRepo.update("view_unpBreakValues", unpBreakSwitch);
        }

        Settings commentsVal = new Settings();
        commentsVal.set_settings_name("view_commentsValues");
        commentsVal.set_settings_val(commentsSwitch);

        if (settingTest("view_commentsValues").equals("")) {
            SettingsRepo.insert(commentsVal);
        } else {
            SettingsRepo.update("view_commentsValues", commentsSwitch);
        }

        Settings companiesVal = new Settings();
        companiesVal.set_settings_name("view_companiesValues");
        companiesVal.set_settings_val(companiesSwitch);

        if (settingTest("view_companiesValues").equals("")) {
            SettingsRepo.insert(companiesVal);
        } else {
            SettingsRepo.update("view_companiesValues", companiesSwitch);
        }

        Settings agenciesVal = new Settings();
        agenciesVal.set_settings_name("view_agenciesValues");
        agenciesVal.set_settings_val(agenciesSwitch);

        if (settingTest("view_agenciesValues").equals("")) {
            SettingsRepo.insert(agenciesVal);
        } else {
            SettingsRepo.update("view_agenciesValues", agenciesSwitch);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(ViewableFields.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(ViewableFields.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(ViewableFields.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_saveVblFields:
                if (_sw_dayName.isChecked()) dayNameSwitch = _sw_dayName.getTextOn().toString();
                else dayNameSwitch = _sw_dayName.getTextOff().toString();
                if (_sw_shift.isChecked()) shiftSwitch = _sw_shift.getTextOn().toString();
                else shiftSwitch = _sw_shift.getTextOff().toString();
                if (_sw_paidHours.isChecked()) paidHoursSwitch = _sw_paidHours.getTextOn().toString();
                else paidHoursSwitch = _sw_paidHours.getTextOff().toString();
                if (_sw_wage.isChecked()) wageSwitch = _sw_wage.getTextOn().toString();
                else wageSwitch = _sw_wage.getTextOff().toString();
                if (_sw_unpBreak.isChecked()) unpBreakSwitch = _sw_unpBreak.getTextOn().toString();
                else unpBreakSwitch = _sw_unpBreak.getTextOff().toString();
                if (_sw_comments.isChecked()) commentsSwitch = _sw_comments.getTextOn().toString();
                else commentsSwitch = _sw_comments.getTextOff().toString();
                if (_sw_companies.isChecked()) companiesSwitch = _sw_companies.getTextOn().toString();
                else companiesSwitch = _sw_companies.getTextOff().toString();
                if (_sw_agencies.isChecked()) agenciesSwitch = _sw_agencies.getTextOn().toString();
                else agenciesSwitch = _sw_agencies.getTextOff().toString();
                saveSwitches();
                Uj_activity = new Intent(ViewableFields.this, Querys.class);
                startActivity(Uj_activity);
                break;
        }
    }
}
