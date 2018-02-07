package uk.co.computerxpert.worktime.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

public class WageManMod extends AppCompatActivity implements View.OnClickListener {

    private EditText in_kezddate, in_vegdate, in_val;
    private int id = 1;
    private Intent Uj_activity;
    private static final String TAG_Ertek="TAG: ";
    private String kezdveg = "k";
    public Spinner spinner1;
    private ListView result;
    private Context context = this;
    private GridView gridView;
    int ed_comp_id;

    private DecimalFormat floatformat = new DecimalFormat(".##");
    Button btn_kezddate, btn_vegdate;

    private Map<String, Integer> months = new HashMap<String, Integer>();

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_man_mod);


        spinner1 = (Spinner) findViewById(R.id.spinner2);
        in_kezddate = (EditText) findViewById(R.id.in_wage_stdateBox);
        in_vegdate = (EditText) findViewById(R.id.in_wage_enddateBox);
        in_val = (EditText) findViewById(R.id.in_wage_valBox);
        btn_kezddate = (Button) findViewById(R.id.btn_wage_stdate);
        btn_vegdate = (Button) findViewById(R.id.btn_wage_enddate);
        result = (ListView) findViewById(R.id.results);

        String fromCompanyID = getIntent().getStringExtra("companyID");
        Button btn_cmmMod = (Button) findViewById(R.id.btn_cmmMod);

        loadFormDefaults(fromCompanyID);
        btn_cmmMod.setOnClickListener(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Companies WHERE " + Companies.KEY_comp_id + " = \"" + value + "\"";
        WageRepo wageRepo = new WageRepo();
        List<Wage> wage_s= wageRepo.getWage(selectQuery);
        // "values" array definition and loading
        ed_comp_id = Integer.parseInt(value);
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<wage_s.size();i++){
            in_kezddate.setText((int) wage_s.get(i).getwage_startdate());
            in_vegdate.setText((int) wage_s.get(i).getwage_enddate());
            in_val.setText(wage_s.get(i).getwage_val());
            values.add(wage_s.get(i).getcomp_name());
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent Uj_activity;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(WageManMod.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(WageManMod.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(WageManMod.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {

    }
}
