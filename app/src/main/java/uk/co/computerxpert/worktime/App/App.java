package uk.co.computerxpert.worktime.App;

/**
 * Created by ricsx on 29/12/17.
 */

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.computerxpert.worktime.Activities.MainActivity;
import uk.co.computerxpert.worktime.Activities.Setup;
import uk.co.computerxpert.worktime.Activities.Worktimes;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.DefShifts;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

public class App extends Application {
    private static Context context;
    private static DBHelper dbHelper;
    private static Integer comp_id;
    public static DecimalFormat dfToHours = new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    public static double OneDayUxt = 86400;
    public static Map<String, Integer> months = new HashMap<String, Integer>();
    public static DecimalFormat dformat = new DecimalFormat("0.00");

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
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


    // Calculate the comp_id from the spinner return value
    public static Integer comp_idFromSpinner(String selectQuery){
        List<Companies> aa = CompaniesRepo.getCompanies2(selectQuery);
        List<Integer> values = new ArrayList<Integer>();
        for(int i=0; i<aa.size();i++){
            values.add(aa.get(i).getcomp_id());
            comp_id = values.get(i);
        }
        return comp_id;
    }


    public static void CompanyListToSpinner(Spinner spinnername, Context context, String selectQuery, String def){

        List<Companies> companies_s= CompaniesRepo.getCompanies(selectQuery);
        List<String> values = new ArrayList<String>();
        if(def!="false"){ values.add(def); }
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name()); }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void DefShiftsListToSpinner(Spinner spinnername, Context context, String selectQuery, String def){

        List<DefShifts> defShifts_s= DefShiftsRepo.getDefShifts(selectQuery);
        List<String> values = new ArrayList<String>();
        if(def!="false"){ values.add(def); }
        for(int i=0; i<defShifts_s.size();i++){ values.add(defShifts_s.get(i).get_defsh_name()); }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void AgenciesListToSpinner(Spinner spinnername, Context context, String selectQuery, String def){

        List<Agencies> agencies_s= AgenciesRepo.getAgencies(selectQuery);
        List<String> values = new ArrayList<String>();
        if(def!="false"){ values.add(def); }
        for(int i=0; i<agencies_s.size();i++){ values.add(agencies_s.get(i).getagency_name()); }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnername.setAdapter(dataAdapter);
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
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
        List<Integer> values = new ArrayList<Integer>();
        for(int i=0; i<aa.size();i++){
            values.add(aa.get(i).getagency_id());
            agency_id = values.get(i);
        }
        return agency_id;
    }
}


