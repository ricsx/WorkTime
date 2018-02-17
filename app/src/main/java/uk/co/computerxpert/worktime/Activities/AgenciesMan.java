package uk.co.computerxpert.worktime.Activities;

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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;

public class AgenciesMan extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    EditText ed_agency_name, edAgencyAddr, edAgencyCity, edAgencyPostCode, edAgencyPhone, edContPName, edContPPhone, edContPEmail;
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencies_man);

        result=(ListView) findViewById(R.id.results);
        ed_agency_name = (EditText) findViewById(R.id.cmm_agencyNameBox3);
        edAgencyAddr = (EditText) findViewById(R.id.cmm_agencyAddressBox3);
        edAgencyCity = (EditText) findViewById(R.id.cmm_agencyCityBox3);
        edAgencyPostCode = (EditText) findViewById(R.id.cmm_agencyPostcodeBox3);
        edAgencyPhone = (EditText) findViewById(R.id.cmm_agencyPhoneBox3);
        edContPName = (EditText) findViewById(R.id.cmm_agencyContPersNameBox3);
        edContPPhone = (EditText) findViewById(R.id.cmm_agencyContPersPhoneBox3);
        edContPEmail = (EditText) findViewById(R.id.cmm_agencyContPersEmailBox3);

        Button btn_stp_agency_send = (Button) findViewById(R.id.btn_stp_agency_update);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.agencies_top);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        make_listview();
        btn_stp_agency_send.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    public void agency_insert(){
        String agency_name =  ed_agency_name.getText().toString();
        String agency_addrs = edAgencyAddr.getText().toString();
        String agency_city = edAgencyCity.getText().toString();
        String agency_postc = edAgencyPostCode.getText().toString();
        String agency_phone = edAgencyPhone.getText().toString();
        String agency_cpname = edContPName.getText().toString();
        String agency_cpphone = edContPPhone.getText().toString();
        String agency_cpemail = edContPEmail.getText().toString();
        Agencies agencies = new Agencies();
        agencies.setagency_name(agency_name);
        agencies.setagencyAddress(agency_addrs);
        agencies.setagencyCity(agency_city);
        agencies.setagencyPostcode(agency_postc);
        agencies.setagencyPhone(agency_phone);
        agencies.setcontactpersonName(agency_cpname);
        agencies.setcontactpersonPhone(agency_cpphone);
        agencies.setcontactpersonEmail(agency_cpemail);
        AgenciesRepo.insert(agencies);
        ed_agency_name.setText("");
        edAgencyAddr.setText("");
        edAgencyCity.setText("");
        edAgencyPostCode.setText("");
        edAgencyPhone.setText("");
        edContPName.setText("");
        edContPPhone.setText("");
        edContPEmail.setText("");
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from Agencies";

        AgenciesRepo agenciesRepo = new AgenciesRepo();
        List<Agencies> agencies_s= agenciesRepo.getAgencies(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<agencies_s.size();i++){ values.add(agencies_s.get(i).getagency_name());  }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        result.setAdapter(adapter);

        // After Clicked...
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) result.getItemAtPosition(position);
                String agencyIDString = Integer.toString(getAgencyIDFromQuery("SELECT * FROM Agencies WHERE agency_name = \""+itemValue+"\""));
                Uj_activity = new Intent(AgenciesMan.this, AgenciesManMod.class);
                Uj_activity.putExtra("agencyID", agencyIDString);
                startActivity(Uj_activity);
            }
        });
    }




    private int getAgencyIDFromQuery(String query){
        String selectQuery =  query;
        int agencyID=0;
        AgenciesRepo agenciesRepo = new AgenciesRepo();
        List<Agencies> agencies_s= agenciesRepo.getAgencies(selectQuery);

        for(int i=0; i<agencies_s.size();i++){ agencyID = agencies_s.get(i).getagency_id(); }

        return agencyID;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(AgenciesMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(AgenciesMan.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(AgenciesMan.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stp_agency_update:
                agency_insert();
                break;
        }
    }

}
