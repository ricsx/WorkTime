package uk.co.computerxpert.worktime.Common;

/*
  Created by ricsx on 29/12/17.
 */

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import uk.co.computerxpert.worktime.Activities.MainActivity;
import uk.co.computerxpert.worktime.Activities.Worktimes;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.DefShifts;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;

public class Common extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Integer comp_id;
    public static double OneDayUxt = 86400;
    public static Map<String, Integer> months = new HashMap<>();
    public static DecimalFormat dformat = new DecimalFormat("0.00");
    @SuppressLint("SimpleDateFormat")
    public static DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    public static final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        DBHelper dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }


    public static void makeMonthArray() {
        months.put("Jan", 1); months.put("Feb", 2); months.put("Mar", 3);
        months.put("Apr", 4); months.put("May", 5); months.put("Jun", 6);
        months.put("Jul", 7); months.put("Aug", 8); months.put("Sep", 9);
        months.put("Oct", 10); months.put("Nov", 11); months.put("Dec", 12);
    }


    public static Context getContext(){
        return context;
    }


    public static void CompanyListToSpinnerAlign(Spinner spinnername, Context context, String def){

        List<Companies> companies_s= CompaniesRepo.getCompanies("SELECT * FROM Companies");
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name()); }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                    R.layout.spinner_align_right, values);
            dataAdapter.setDropDownViewResource(R.layout.spinner_align_right);
            spinnername.setAdapter(dataAdapter);
    }


    public static void CompanyListToSpinner(Spinner spinnername, Context context, String selectQuery, String def){

        List<Companies> companies_s= CompaniesRepo.getCompanies(selectQuery);
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name()); }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void DefShiftsListToSpinner(Spinner spinnername, Context context, String def){

        List<DefShifts> defShifts_s= DefShiftsRepo.getDefShifts("SELECT * FROM DefShifts");
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<defShifts_s.size();i++) values.add(defShifts_s.get(i).get_defsh_name());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void AgenciesListToSpinner(Spinner spinnername, Context context, String def){

        List<Agencies> agencies_s= AgenciesRepo.getAgencies("SELECT * FROM Agencies");
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<agencies_s.size();i++){
            if(!agencies_s.get(i).getagency_name().equals("-")) {
                values.add(agencies_s.get(i).getagency_name());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void AgenciesListToSpinnerAlign(Spinner spinnername, Context context, String def){

        List<Agencies> agencies_s= AgenciesRepo.getAgencies("SELECT * FROM Agencies");
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<agencies_s.size();i++) {
            if (!agencies_s.get(i).getagency_name().equals("-")) {
                values.add(agencies_s.get(i).getagency_name());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_align_right, values);
        dataAdapter.setDropDownViewResource(R.layout.spinner_align_right);
        spinnername.setAdapter(dataAdapter);
    }


    public static Double wageFromWageID(int comp_id){
        double wage_val = 0.00;
        String selectQuery= "SELECT * FROM wage WHERE wage_comp_id= \"" + comp_id + "\"";
        List<Wage> wages_s = WageRepo.getWage(selectQuery);
        for(int i=0;i<wages_s.size();i++){
            wage_val = Double.parseDouble(wages_s.get(i).getwage_val());
        }
        return wage_val;
    }

    public static Integer agency_idFromSpinner(String selectQuery){
        Integer agency_id=0;
        List<Agencies> aa = AgenciesRepo.getAgencies(selectQuery);
        List<Integer> values = new ArrayList<>();
        for(int i=0; i<aa.size();i++){
            values.add(aa.get(i).getagency_id());
            agency_id = values.get(i);
        }
        return agency_id;
    }


    // Calculate the comp_id from the spinner return value
    public static Integer comp_idFromSpinner(String selectQuery){
        List<Companies> aa = CompaniesRepo.getCompanies2(selectQuery);
        List<Integer> values = new ArrayList<>();
        for(int i=0; i<aa.size();i++){
            values.add(aa.get(i).getcomp_id());
            comp_id = values.get(i);
        }
        return comp_id;
    }


    public static String settingTest(String settingName){
        String settings_val = "";
        String selectQuery="SELECT * FROM Settings WHERE settings_name=\""+settingName+"\"";
        List<Settings> settings_s = SettingsRepo.getSettings(selectQuery);
        for(int i=0; i<settings_s.size(); i++) {
            settings_val = settings_s.get(i).get_settings_val();
        } return settings_val;
    }


    public static void YearsToSpinnerAlign(Spinner spinnername, Context context, String def){

        List<uk.co.computerxpert.worktime.data.model.Worktimes> worktimes_s= WorktimesRepo.listYears("SELECT distinct wt_year FROM Worktime");
        List<String> values = new ArrayList<>();
        if(!Objects.equals(def, "false")){ values.add(def); }
        for(int i=0; i<worktimes_s.size();i++) values.add(String.valueOf(worktimes_s.get(i).getwt_year()));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }

}


