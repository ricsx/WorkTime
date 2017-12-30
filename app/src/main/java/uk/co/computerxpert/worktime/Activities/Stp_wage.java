package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

public class Stp_wage extends AppCompatActivity  implements View.OnClickListener {

    private EditText in_kezddate;
    private EditText in_vegdate;
    private EditText in_val;
    private int id = 1;
    private Intent Uj_activity;
    private static final String TAG_Ertek="TAG: ";
    private String kezdveg = "k";
    private Spinner spinner1;
    private ListView result;

    Button btn_kezddate, btn_vegdate;

    private Map<String, Integer> months = new HashMap<String, Integer>();

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stp_wages);

        spinner1 = (Spinner) findViewById(R.id.spinner2);
        in_kezddate = (EditText) findViewById(R.id.in_wage_stdateBox);
        in_vegdate = (EditText) findViewById(R.id.in_wage_enddateBox);
        in_val = (EditText) findViewById(R.id.in_wage_valBox);
        btn_kezddate = (Button) findViewById(R.id.btn_wage_stdate);
        btn_vegdate = (Button) findViewById(R.id.btn_wage_enddate);

        // Upload and start of the Spinner (Company names)
        make_listviewtospinner();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_kezddate.setOnClickListener(this);
        btn_vegdate.setOnClickListener(this);

        months.put("Jan",1); months.put("Feb",2); months.put("Mar",3); months.put("Apr",4); months.put("May",5);
        months.put("Jun",6); months.put("Jul",7); months.put("Aug",8); months.put("Sep",9); months.put("Oct",10);
        months.put("Nov",11); months.put("Dec",12);

        btn_kezddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg="k";
                updateDate();
            }
        });

        btn_vegdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kezdveg = "v";
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
        if(kezdveg == "v") {
            if (var == "date") {
                in_vegdate.setText(formatDate.format(dateTime.getTime()));
            }
        }
    }


    private void make_listviewtospinner(){

        CompaniesRepo companiesRepo = new CompaniesRepo();
        List<Companies> companies_s= companiesRepo.getCompanies();

        List<String> values = new ArrayList<String>();
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name());  }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }


    public void newWage (View view) throws ParseException {
        // MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String cegnev = spinner1.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String veg_ = vegdate+" 00:00";
        float val =  Float.valueOf(in_val.getText().toString());

        // Dates convert to Unix format
        // DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");

        Date date_kezd = dateFormat.parse(kezd_);
        long kezd_uxT = (long)date_kezd.getTime()/1000;

        Date date_veg = dateFormat.parse(veg_);
        long veg_uxT = (long)date_veg.getTime()/1000;
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

        int woyear = now.get(Calendar.WEEK_OF_YEAR);

        // End of week-of-year calculate

        Wage wage = new Wage();
        wage.setwage_comp_id(1);
        wage.setwage_startdate(kezd_uxT);
        wage.setwage_enddate(veg_uxT);
        wage.setwage_val(val);

        // Write datas into DB
        WageRepo.insert(wage);

        in_kezddate.setText("");
        in_vegdate.setText("");
        in_val.setText("");

    }




    private void make_listview(){

        CompaniesRepo companiesRepo = new CompaniesRepo();
        List<Companies> companies_s= companiesRepo.getCompanies();

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name());  }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        result.setAdapter(adapter);

        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) result.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(Stp_wage.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Stp_wage.this, Dashbrd.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Stp_wage.this, Setup.class);
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

/*
    public void company_insert(){
        String comp_name =  ed_comp_name.getText().toString();
        Companies companies = new Companies();
        companies.setcomp_name(comp_name);
        CompaniesRepo.insert(companies);
        ed_comp_name.setText("");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stp_comp_send:
                company_insert();
                Log.i(TAG_Ertek, "send");
                break;
        }
    }
*/
}
