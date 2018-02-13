package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

import static uk.co.computerxpert.worktime.App.App.dateTime;
import static uk.co.computerxpert.worktime.App.App.formatDate;
import static uk.co.computerxpert.worktime.App.App.makeMonthArray;
import static uk.co.computerxpert.worktime.App.App.months;

public class WageMan extends AppCompatActivity  implements View.OnClickListener {

    private EditText in_kezddate, in_val;
    private Intent Uj_activity;
    private String kezdveg = "k";
    public Spinner spinner1;
    private ListView result;
    private Context context = this;

    Button btn_kezddate;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_man);

        spinner1 = (Spinner) findViewById(R.id.spinner2);
        in_kezddate = (EditText) findViewById(R.id.in_wage_stdateBox);
        in_val = (EditText) findViewById(R.id.in_wage_valBox);
        btn_kezddate = (Button) findViewById(R.id.btn_wage_stdate);
        result = (ListView) findViewById(R.id.results);

        make_listview();
        makeMonthArray();
        // starting Spinner (Company names)
        String selectQuery = "SELECT * FROM companies";
        App.CompanyListToSpinner(spinner1, context, selectQuery, "false");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_kezddate.setOnClickListener(this);

        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
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
        if(kezdveg == "k") {
            if (var == "date") {
                in_kezddate.setText(formatDate.format(dateTime.getTime()));
            }
        }
    }


    public void newWage (View view) throws ParseException {

        String cegnev = spinner1.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String val = in_val.getText().toString();

        String selectQuery =  " SELECT Companies." + Companies.KEY_comp_id
                + ", Companies." + Companies.KEY_comp_name
                + " FROM " + Companies.TABLE
                + " WHERE " + Companies.KEY_comp_name
                + " =\""+ cegnev+"\""
                ;

        Integer comp_id = App.comp_idFromSpinner(selectQuery);

        // Dates convert to Unix format
        // DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");

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

        // End of week-of-year calculate

        Wage wage = new Wage();
        wage.setwage_comp_id(comp_id);
        wage.setwage_startdate(kezd_uxT);
        wage.setwage_strstdate(kezddate);
        wage.setwage_val(val);

        // Write datas into DB
        WageRepo.insert(wage);

        in_kezddate.setText("");
        in_val.setText("");
    }


    private void make_listview(){
        String selectQuery =  " SELECT Wage." + Wage.KEY_wage_id
                + ", Wage." + Wage.KEY_wage_comp_id
                + ", Wage." + Wage.KEY_wage_startdate
                + ", Wage." + Wage.KEY_wage_strstdate
                + ", Wage." + Wage.KEY_wage_val
                + ", Companies." + Companies.KEY_comp_name
                + " FROM " + Wage.TABLE
                + ", " + Companies.TABLE
                + " WHERE " + Wage.KEY_wage_comp_id
                + " = " + Companies.KEY_comp_id
                ;
        WageRepo wageRepo = new WageRepo();
        List<Wage> wage_s = wageRepo.relGetWage(selectQuery);

        // "values" array definition and loading
        ArrayList<Integer> arr_id = new ArrayList<>();
        ArrayList<Integer> arr_comp_id = new ArrayList<>();
        ArrayList<Double> arr_startdate = new ArrayList<>();
        ArrayList<String> arr_strstdate = new ArrayList<>();
        ArrayList<String> arr_val = new ArrayList<String>();
        ArrayList<String> arr_compname = new ArrayList<String>();
        ArrayList<String> list_val = new ArrayList<String>();

        for(int i=0; i<wage_s.size();i++){
            arr_id.add(wage_s.get(i).getwage_id());
            arr_comp_id.add(wage_s.get(i).getwage_comp_id());
            arr_startdate.add(wage_s.get(i).getwage_startdate());
            arr_strstdate.add(wage_s.get(i).getwage_strstdate());
            arr_val.add(wage_s.get(i).getwage_val());
            arr_compname.add(wage_s.get(i).getcomp_name());
            long dv = (long) (Double.valueOf(wage_s.get(i).getwage_startdate())*1000);
            Date df = new java.util.Date(dv);

            String df_arr_st = new SimpleDateFormat("MM/dd, yyyy").format(df);
            list_val.add(wage_s.get(i).getcomp_name()+" - "+df_arr_st+" - "+wage_s.get(i).getwage_val());
        }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list_val);
        result.setAdapter(adapter);

        // TODO: To create the modify of records (NO DELETE! modify only)
        // After Clicked...
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) result.getItemAtPosition(position);
                List<String> cutteredItems = Arrays.asList(itemValue.split(" - "));
                String wageIDString = Integer.toString(getWageIDFromQuery( "SELECT * FROM  wage,Companies WHERE " +
                        "wage.wage_comp_id=companies.comp_id AND comp_name = \""+ cutteredItems.get(0) +"\"" +
                        " AND wage_val = \""+ cutteredItems.get(2) + "\""));;
                Uj_activity = new Intent(WageMan.this, WageManMod.class);
                // Uj_activity.putExtra("companyID", companyIDString);
                Uj_activity.putExtra("wageID", wageIDString);
                startActivity(Uj_activity);

            }
        });


    }


    private int getWageIDFromQuery(String query){
        String selectQuery =  query;
        int wageID=0;
        WageRepo wageRepo = new WageRepo();
        List<Wage> wages_s= wageRepo.relGetWage(selectQuery);

        for(int i=0; i<wages_s.size();i++){ wageID = wages_s.get(i).getwage_id(); }

        return wageID;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(WageMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(WageMan.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(WageMan.this, Setup.class);
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
