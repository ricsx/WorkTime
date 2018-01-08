package uk.co.computerxpert.worktime.App;

/**
 * Created by ricsx on 29/12/17.
 */

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;

public class App extends Application {
    private static Context context;
    private static DBHelper dbHelper;
    private static Integer comp_id;
    private static final String TAG_Ertek="TAG: ";

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }


    public static Context getContext(){
        return context;
    }


    // Calculate the comp_id from the spinner return value
    public static Integer comp_idFromSpinner(String selectQuery){
        List<Companies> aa = CompaniesRepo.getCompanies(selectQuery);
        List<Integer> values = new ArrayList<Integer>();
        for(int i=0; i<aa.size();i++){
            values.add(aa.get(i).getcomp_id());
            comp_id = values.get(i);
        }
        return comp_id;
    }


    public static void CompanyListToSpinner(Spinner spinnername, Context context, String selectQuery){

        CompaniesRepo companiesRepo = new CompaniesRepo();
        List<Companies> companies_s= CompaniesRepo.getCompanies(selectQuery);
        List<String> values = new ArrayList<String>();
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name()); }
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

}


