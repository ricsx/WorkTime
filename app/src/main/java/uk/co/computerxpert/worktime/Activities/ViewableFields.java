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
    Switch _sw_dayName, _sw_shift, _sw_paidHours, _sw_wage, _sw_unpBreak,
        _sw_agencies, _sw_companies, _sw_comments;
    Button _btn_saveVblFields;
    String dayNameSwitch;

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
                saveSwitches();
                Uj_activity = new Intent(ViewableFields.this, ViewableFields.class);
                startActivity(Uj_activity);
                break;
        }
    }
}
