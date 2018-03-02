package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
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
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        spinner1 = findViewById(R.id.spinner2);
        in_kezddate = findViewById(R.id.in_wage_stdateBox);
        in_val = findViewById(R.id.in_wage_valBox);
        btn_kezddate = findViewById(R.id.btn_wage_stdate);
        result = findViewById(R.id.results);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        String firstRunFlag = getIntent().getStringExtra("firstRunFlag");
        if(firstRunFlag == null){ firstRunFlag ="0"; }

        Toolbar myToolbar = findViewById(R.id.wages_man_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        make_listview();
        makeMonthArray();
        // starting Spinner (Company names)
        String selectQuery = "SELECT * FROM companies";
        App.CompanyListToSpinner(spinner1, context, selectQuery, "false");

        if(firstRunFlag.equals("0")) {
            navigation.setVisibility(View.VISIBLE);
        }else{
            navigation.setVisibility(View.GONE);
        }
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
            updateTextLabel();
        }
    };


    private void updateTextLabel(){
        if(Objects.equals(kezdveg, "k")) {
                in_kezddate.setText(formatDate.format(dateTime.getTime()));
                btn_kezddate.setText(formatDate.format(dateTime.getTime()));
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
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");

        Date date_kezd = dateFormat.parse(kezd_);
        long kezd_uxT = date_kezd.getTime() /1000;

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

        List<Wage> wage_s = WageRepo.relGetWage(selectQuery);

        // "values" array definition and loading
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<Integer> arr_id = new ArrayList<>();
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<Integer> arr_comp_id = new ArrayList<>();
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<Double> arr_startdate = new ArrayList<>();
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> arr_strstdate = new ArrayList<>();
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> arr_val = new ArrayList<>();
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> arr_compname = new ArrayList<>();
        ArrayList<String> list_val = new ArrayList<>();

        for(int i=0; i<wage_s.size();i++){
            arr_id.add(wage_s.get(i).getwage_id());
            arr_comp_id.add(wage_s.get(i).getwage_comp_id());
            arr_startdate.add(wage_s.get(i).getwage_startdate());
            arr_strstdate.add(wage_s.get(i).getwage_strstdate());
            arr_val.add(wage_s.get(i).getwage_val());
            arr_compname.add(wage_s.get(i).getcomp_name());
            long dv = (long) (wage_s.get(i).getwage_startdate() *1000);
            Date df = new java.util.Date(dv);

            @SuppressLint("SimpleDateFormat") String df_arr_st = new SimpleDateFormat("MM/dd, yyyy").format(df);
            list_val.add(wage_s.get(i).getcomp_name()+" - "+df_arr_st+" - "+wage_s.get(i).getwage_val());
        }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list_val);
        result.setAdapter(adapter);

        // After Clicked...
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemValue = (String) result.getItemAtPosition(position);
                List<String> cutteredItems = Arrays.asList(itemValue.split(" - "));
                String wageIDString = Integer.toString(getWageIDFromQuery( "SELECT * FROM  wage,Companies WHERE " +
                        "wage.wage_comp_id=companies.comp_id AND comp_name = \""+ cutteredItems.get(0) +"\"" +
                        " AND wage_val = \""+ cutteredItems.get(2) + "\""));
                Uj_activity = new Intent(WageMan.this, WageManMod.class);
                // Uj_activity.putExtra("companyID", companyIDString);
                Uj_activity.putExtra("wageID", wageIDString);
                Uj_activity.putExtra("compName", cutteredItems.get(0));

                startActivity(Uj_activity);

            }
        });
    }


    private int getWageIDFromQuery(String query){
        int wageID=0;
        List<Wage> wages_s= WageRepo.relGetWage(query);

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
