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
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;

public class CompaniesMan extends AppCompatActivity  implements View.OnClickListener {

    private Intent Uj_activity;
    EditText ed_comp_name, edCompAddr, edCompCity, edCompPostCode, edCompPhone, edContPName, edContPPhone, edContPEmail;
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_man);

        result= findViewById(R.id.results);
        ed_comp_name = findViewById(R.id.cmm_compNameBox3);
        edCompAddr = findViewById(R.id.cmm_compAddressBox3);
        edCompCity = findViewById(R.id.cmm_compCityBox3);
        edCompPostCode = findViewById(R.id.cmm_compPostcodeBox3);
        edCompPhone = findViewById(R.id.cmm_compPhoneBox3);
        edContPName = findViewById(R.id.cmm_compContPersNameBox3);
        edContPPhone = findViewById(R.id.cmm_compContPersPhoneBox3);
        edContPEmail = findViewById(R.id.cmm_compContPersEmailBox3);

        String firstRunFlag = getIntent().getStringExtra("firstRunFlag");
        if(firstRunFlag == null){ firstRunFlag ="0"; }

        Button btn_stp_comp_send = findViewById(R.id.btn_stp_comp_send);

        Toolbar myToolbar = findViewById(R.id.companies_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        make_listview();
        btn_stp_comp_send.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(firstRunFlag.equals("0")) {
            navigation.setVisibility(View.VISIBLE);
        }else{
            navigation.setVisibility(View.GONE);
        }
    }



    public void company_insert(){
        String comp_name =  ed_comp_name.getText().toString();
        String comp_addrs = edCompAddr.getText().toString();
        String comp_city = edCompCity.getText().toString();
        String comp_postc = edCompPostCode.getText().toString();
        String comp_phone = edCompPhone.getText().toString();
        String comp_cpname = edContPName.getText().toString();
        String comp_cpphone = edContPPhone.getText().toString();
        String comp_cpemail = edContPEmail.getText().toString();
        int comp_agencyid = 0;
        Companies companies = new Companies();
        companies.setcomp_name(comp_name);
        companies.setcompanyAddress(comp_addrs);
        companies.setcompanyCity(comp_city);
        companies.setcompanyPostcode(comp_postc);
        companies.setcompanyPhone(comp_phone);
        companies.setcontactpersonName(comp_cpname);
        companies.setcontactpersonPhone(comp_cpphone);
        companies.setcontactpersonEmail(comp_cpemail);
        companies.setcompanyAgencyID(comp_agencyid);
        CompaniesRepo.insert(companies);
        ed_comp_name.setText("");
        edCompAddr.setText("");
        edCompCity.setText("");
        edCompPostCode.setText("");
        edCompPhone.setText("");
        edContPName.setText("");
        edContPPhone.setText("");
        edContPEmail.setText("");
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from Companies";

        List<Companies> companies_s= CompaniesRepo.getCompanies(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<companies_s.size();i++){ values.add(companies_s.get(i).getcomp_name());  }

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
                String companyIDString = Integer.toString(getCompanyIDFromQuery("SELECT * FROM Companies WHERE comp_name = \""+itemValue+"\""));
                Uj_activity = new Intent(CompaniesMan.this, CompaniesManMod.class);
                Uj_activity.putExtra("companyID", companyIDString);
                startActivity(Uj_activity);
             }
         });
    }




    private int getCompanyIDFromQuery(String query){
        int companyID=0;

        List<Companies> companies_s= CompaniesRepo.getCompanies(query);

        for(int i=0; i<companies_s.size();i++){ companyID = companies_s.get(i).getcomp_id(); }

        return companyID;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(CompaniesMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(CompaniesMan.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(CompaniesMan.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stp_comp_send:
                company_insert();
                break;
        }
    }

}
