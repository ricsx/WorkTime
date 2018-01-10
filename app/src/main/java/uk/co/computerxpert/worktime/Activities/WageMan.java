package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;

public class WageMan extends AppCompatActivity  implements View.OnClickListener {

    private EditText in_kezddate;
    private EditText in_vegdate;
    private EditText in_val;
    private int id = 1;
    private Intent Uj_activity;
    private static final String TAG_Ertek="TAG: ";
    private String kezdveg = "k";
    public Spinner spinner1;
    private ListView result;
    private Context context = this;
    private GridView gridView;

    private DecimalFormat floatformat = new DecimalFormat(".##");
    Button btn_kezddate, btn_vegdate;

    private Map<String, Integer> months = new HashMap<String, Integer>();

    final Calendar dateTime = Calendar.getInstance(Locale.UK); // Set up Monday as first day of week
    DateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.UK); // Set up time format to 24-hour

    String companies[] = {"Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung", "faszom"};
    String os[] = {"Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada", "FASZOM"};


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_man);

        spinner1 = (Spinner) findViewById(R.id.spinner2);
        in_kezddate = (EditText) findViewById(R.id.in_wage_stdateBox);
        in_vegdate = (EditText) findViewById(R.id.in_wage_enddateBox);
        in_val = (EditText) findViewById(R.id.in_wage_valBox);
        btn_kezddate = (Button) findViewById(R.id.btn_wage_stdate);
        btn_vegdate = (Button) findViewById(R.id.btn_wage_enddate);
        result=(ListView) findViewById(R.id.result);

        make_listview();

        addHeaders();
        addData();

        // starting Spinner (Company names)
        String selectQuery = "SELECT * FROM companies";
        App.CompanyListToSpinner(spinner1, context, selectQuery);

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


    public void newWage (View view) throws ParseException {
        // MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String cegnev = spinner1.getSelectedItem().toString();
        String kezddate = in_kezddate.getText().toString();
        String vegdate = in_vegdate.getText().toString();
        String kezd_ = kezddate+" 00:00";
        String veg_ = vegdate+" 00:00";
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
        wage.setwage_comp_id(comp_id);
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
        String selectQuery =  " SELECT Wage." + Wage.KEY_wage_id
                + ", Wage." + Wage.KEY_wage_comp_id
                + ", Wage." + Wage.KEY_wage_startdate
                + ", Wage." + Wage.KEY_wage_enddate
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
        ArrayList<Long> arr_startdate = new ArrayList<>();
        ArrayList<Long> arr_enddate = new ArrayList<>();
        ArrayList<String> arr_val = new ArrayList<String>();
        ArrayList<String> arr_compname = new ArrayList<String>();
        ArrayList<String> list_val = new ArrayList<String>();
        //ArrayList<String> df_arr_st = new ArrayList<>();
       // ArrayList<String> df_arr_en = new ArrayList<>();

        for(int i=0; i<wage_s.size();i++){
            arr_id.add(wage_s.get(i).getwage_id());
            arr_comp_id.add(wage_s.get(i).getwage_comp_id());
            arr_startdate.add(wage_s.get(i).getwage_startdate());
            arr_enddate.add(wage_s.get(i).getwage_enddate());
            arr_val.add(wage_s.get(i).getwage_val());
            arr_compname.add(wage_s.get(i).getcomp_name());
            long dv = Long.valueOf(wage_s.get(i).getwage_startdate())*1000;
            Date df = new java.util.Date(dv);
            // Date df = new java.util.Date(wage_s.get(i).getwage_startdate());

            String df_arr_st = new SimpleDateFormat("MM/dd, yyyy").format(df);
            list_val.add(wage_s.get(i).getcomp_name()+"  "+df_arr_st+"  "+wage_s.get(i).getwage_val());
        }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list_val);
        result.setAdapter(adapter);

        // TODO: To create the modify of records (NO DELETE! just modify)
        // After Clicked...
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) result.getItemAtPosition(position);

                String companyIDString = Integer.toString(getWageIDFromQuery( "SELECT * FROM  wage,Companies WHERE wage.wage_comp_id=companies.comp_id AND wage_name = \""+itemValue+"\""));
                Uj_activity = new Intent(WageMan.this, WageManMod.class);
                // Uj_activity.putExtra("companyID", companyIDString);
                Uj_activity.putExtra("companyID", 1);
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
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(WageMan.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(WageMan.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };
/*
    @Override
    public void onClick(View v) {

    }
*/



    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "COMPANY", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "UJ", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        int numCompanies = companies.length;
        TableLayout tl = findViewById(R.id.table);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, companies[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, os[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, os[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));

            tl.addView(tr, getTblLayoutParams());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row :: " + id);
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    {

    }



}
