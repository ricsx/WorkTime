package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.computerxpert.worktime.Common.Common;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;

import static uk.co.computerxpert.worktime.Common.Common.settingTest;

public class Setup extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    private EditText ed_currency, _viewNumWeeks;
    Switch _sw_beforeValue;
    String statusSwitch1;
    Spinner _sp_weekList;
    Map<String, Integer> daysOfWeek = new HashMap<>();
    List<String> daysToSpinner = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        Button companies = findViewById(R.id.btn_companies2);
        Button wages = findViewById(R.id.btn_wages2);
        Button dev = findViewById(R.id.btn_dev);
        Button agencies = findViewById(R.id.btn_agencies2);
        Button defShifts = findViewById(R.id.btn_defShifts2);
        Button btn_saveCurrency = findViewById(R.id.btn_saveCurrency);
        Button _deleteDays = findViewById(R.id.btn_deletedays);
        Button btn_saveNumWeeks = findViewById(R.id.btn_saveNumWeeks);
        Button btn_contactSupport = findViewById(R.id.btn_contactSupport);
        Button btn_reportBugs = findViewById(R.id.btn_reportBugs);
        Button btn_saveStartOfWeek = findViewById(R.id.btn_saveStartOfWeek);

        _sp_weekList = findViewById(R.id.sp_weekList);
        ed_currency = findViewById(R.id.ed_currency);
        _sw_beforeValue = findViewById(R.id.sw_dayName);
        _viewNumWeeks = findViewById(R.id.ed_numWeeks);

        loadSwitchDefaults(_sw_beforeValue);
        _sw_beforeValue.setTextOn("true");
        _sw_beforeValue.setTextOff("false");

        makeDaysArrayMulti();
        makeDaysArray();
        Common.daysToSpinner(_sp_weekList, this, daysToSpinner, "false");
        loadDefaults();

        companies.setOnClickListener(this);
        wages.setOnClickListener(this);
        dev.setOnClickListener(this);
        agencies.setOnClickListener(this);
        defShifts.setOnClickListener(this);
        btn_saveCurrency.setOnClickListener(this);
        _deleteDays.setOnClickListener(this);
        btn_saveNumWeeks.setOnClickListener(this);
        btn_contactSupport.setOnClickListener(this);
        btn_reportBugs.setOnClickListener(this);
        btn_saveStartOfWeek.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
    }


    public void loadDefaults(){
        String selectQuery;
        List<Settings> settings_s;
        selectQuery = " SELECT * from Settings WHERE " + Settings.KEY_settings_name + " = \"currency\"";
        settings_s = SettingsRepo.getSettings(selectQuery);
        //noinspection MismatchedQueryAndUpdateOfCollection
        for (int i = 0; i < settings_s.size(); i++) ed_currency.setText(settings_s.get(i).get_settings_val());
        selectQuery = " SELECT * from Settings WHERE " + Settings.KEY_settings_name + " = \"viewNumWeeks\"";
        settings_s = SettingsRepo.getSettings(selectQuery);
        for (int i = 0; i < settings_s.size(); i++) _viewNumWeeks.setText(settings_s.get(i).get_settings_val());

        String dayNameFromDB="";
        String selectQuery2 = " SELECT * FROM Settings WHERE settings_name=\"startOfTheWeek\"";
        List<Settings> daynm = SettingsRepo.getSettings(selectQuery2);
        for(int i=0; i<daynm.size();i++) { dayNameFromDB = daynm.get(i).get_settings_val(); }
        _sp_weekList.setSelection(Integer.parseInt(dayNameFromDB)-1);
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
            case R.id.btn_saveNumWeeks:
                String viewNumWeeks = _viewNumWeeks.getText().toString();
                Settings settings = new Settings();
                settings.set_settings_name("viewNumWeeks");
                settings.set_settings_val(viewNumWeeks);
                if (settingTest("viewNumWeeks").equals("")){ SettingsRepo.insert(settings); }
                else { SettingsRepo.update("viewNumWeeks", viewNumWeeks);
                }
                Uj_activity = new Intent(Setup.this, Setup.class);
                startActivity(Uj_activity);
                break;
            case R.id.btn_contactSupport:
                sendEmail("eaddrss@gmail.com", "TO SUPPORT");
                break;
            case R.id.btn_reportBugs:
                sendEmail("eaddrss@gmail.com", "BUGREPORT");
                break;
            case R.id.btn_saveStartOfWeek:
                String dayName = _sp_weekList.getSelectedItem().toString();
                String dayNum = Integer.toString(daysOfWeek.get(dayName));
                Settings daySettings = new Settings();
                daySettings.set_settings_name("startOfTheWeek");
                daySettings.set_settings_val(dayNum);
                if (settingTest("startOfTheWeek").equals("")){ SettingsRepo.insert(daySettings); }
                else { SettingsRepo.update("startOfTheWeek", dayNum);
                }
                Uj_activity = new Intent(Setup.this, Setup.class);
                startActivity(Uj_activity);
                break;
        }
    }


    protected void sendEmail(String to, String subject) {

      String[] TO = {to};
      Intent emailIntent = new Intent(Intent.ACTION_SEND);
      emailIntent.setData(Uri.parse("mailto:"));
      emailIntent.setType("text/plain");

      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
      emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

      try {
         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
         // finish();
      } catch (android.content.ActivityNotFoundException ex) {
         Toast.makeText(Setup.this,
         "There is no email client installed.", Toast.LENGTH_SHORT).show();
      }
   }


    public void makeDaysArray() {
        daysToSpinner.add(getString(R.string.monday)); daysToSpinner.add(getString(R.string.tuesday));
        daysToSpinner.add(getString(R.string.wednesday)); daysToSpinner.add(getString(R.string.thursday));
        daysToSpinner.add(getString(R.string.friday)); daysToSpinner.add(getString(R.string.saturday));
        daysToSpinner.add(getString(R.string.sunday));
    }

    public void makeDaysArrayMulti() {
        daysOfWeek.put(getString(R.string.monday),1); daysOfWeek.put(getString(R.string.tuesday),2);
        daysOfWeek.put(getString(R.string.wednesday),3); daysOfWeek.put(getString(R.string.thursday),4);
        daysOfWeek.put(getString(R.string.friday),5); daysOfWeek.put(getString(R.string.saturday),6);
        daysOfWeek.put(getString(R.string.sunday),7);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Setup.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Setup.this, MainActivity.class);
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
