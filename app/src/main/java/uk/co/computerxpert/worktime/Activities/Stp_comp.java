package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;

public class Stp_comp extends AppCompatActivity  implements View.OnClickListener {

    private Intent Uj_activity;
    private int id=1;
    private static final String TAG_Ertek="TAG: ";
    EditText ed_comp_name;
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stp_comp);

        TextView tv_str_comp = (TextView) findViewById(R.id.tv_stp_comp);
        result=(ListView) findViewById(R.id.result);
        ed_comp_name = (EditText) findViewById(R.id.in_comp_nameBox);
        Button btn_stp_comp_send = (Button) findViewById(R.id.btn_stp_comp_send);

        make_listview();
        btn_stp_comp_send.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void make_listview(){
        String selectQuery =  " SELECT Companies." + Companies.KEY_comp_id
                + ", Companies." + Companies.KEY_comp_name
                + " FROM " + Companies.TABLE
                ;
        CompaniesRepo companiesRepo = new CompaniesRepo();
        List<Companies> companies_s= companiesRepo.findCompanies(selectQuery);

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
                    Uj_activity = new Intent(Stp_comp.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(Stp_comp.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(Stp_comp.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


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

}
