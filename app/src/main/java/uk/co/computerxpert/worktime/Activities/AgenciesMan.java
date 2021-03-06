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

import uk.co.computerxpert.worktime.Common.Common;
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

        Common.setStatusBarColor(this.getWindow(), this);

        result= findViewById(R.id.results);
        ed_agency_name = findViewById(R.id.cmm_personLastNameBox);
        edAgencyAddr = findViewById(R.id.cmm_personAddressBox2);
        edAgencyCity = findViewById(R.id.cmm_personCityBox2);
        edAgencyPostCode = findViewById(R.id.cmm_personPostcodeBox2);
        edAgencyPhone = findViewById(R.id.cmm_personPhoneBox2);
        edContPName = findViewById(R.id.cmm_agencyContPersNameBox3);
        edContPPhone = findViewById(R.id.cmm_agencyContPersPhoneBox3);
        edContPEmail = findViewById(R.id.cmm_agencyContPersEmailBox3);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        String firstRunFlag = getIntent().getStringExtra("firstRunFlag");
        if(firstRunFlag == null){ firstRunFlag ="0"; }

        Button btn_stp_agency_send = findViewById(R.id.btn_stp_person_update2);

        Toolbar myToolbar = findViewById(R.id.agencies_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        make_listview();
        btn_stp_agency_send.setOnClickListener(this);

        if(firstRunFlag.equals("0")) {
            navigation.setVisibility(View.VISIBLE);
        }else{
            navigation.setVisibility(View.GONE);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
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
        finish();
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from Agencies";

        // AgenciesRepo agenciesRepo = new AgenciesRepo();
        List<Agencies> agencies_s= AgenciesRepo.getAgencies(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<agencies_s.size();i++){ values.add(agencies_s.get(i).getagency_name());  }

        // array-fetching
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        result.setAdapter(adapter);

        // After Clicked...
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemValue = (String) result.getItemAtPosition(position);
                String agencyIDString = Integer.toString(getAgencyIDFromQuery("SELECT * FROM Agencies WHERE agency_name = \""+itemValue+"\""));
                Uj_activity = new Intent(AgenciesMan.this, AgenciesManMod.class);
                Uj_activity.putExtra("agencyID", agencyIDString);
                startActivity(Uj_activity);
            }
        });
    }




    private int getAgencyIDFromQuery(String query){
        int agencyID=0;
        List<Agencies> agencies_s= AgenciesRepo.getAgencies(query);

        for(int i=0; i<agencies_s.size();i++){ agencyID = agencies_s.get(i).getagency_id(); }

        return agencyID;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(AgenciesMan.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(AgenciesMan.this, MainActivity.class);
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
            case R.id.btn_stp_person_update2:
                agency_insert();
                break;
        }
    }

}
