package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;

public class CompaniesManMod extends AppCompatActivity implements View.OnClickListener {

    int ed_comp_id;
    EditText ed_comp_name;
    EditText edCompAddr;
    EditText edCompCity;
    EditText edCompPostCode;
    EditText edCompPhone;
    EditText edContPName;
    EditText edContPPhone;
    EditText edContPEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_man_mod);

        ed_comp_name = (EditText) findViewById(R.id.cmm_compNameBox);
        edCompAddr = (EditText) findViewById(R.id.cmm_compAddressBox);
        edCompCity = (EditText) findViewById(R.id.cmm_compCityBox);
        edCompPostCode = (EditText) findViewById(R.id.cmm_compPostcodeBox);
        edCompPhone = (EditText) findViewById(R.id.cmm_compPhoneBox);
        edContPName = (EditText) findViewById(R.id.cmm_compContPersNameBox);
        edContPPhone = (EditText) findViewById(R.id.cmm_compContPersPhoneBox);
        edContPEmail = (EditText) findViewById(R.id.cmm_compContPersEmailBox);

        String fromCompanyID = getIntent().getStringExtra("companyID");
        Button btn_cmmMod = (Button) findViewById(R.id.btn_cmmMod);

        loadFormDefaults(fromCompanyID);
        btn_cmmMod.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Companies WHERE " + Companies.KEY_comp_id + " = \"" + value + "\"";
        CompaniesRepo companiesRepo = new CompaniesRepo();
        List<Companies> companies_s= companiesRepo.getCompanies(selectQuery);
        // "values" array definition and loading
        ed_comp_id = Integer.parseInt(value);
        ArrayList<String> values = new ArrayList<String>();
        for(int i=0; i<companies_s.size();i++){
            ed_comp_name.setText(companies_s.get(i).getcomp_name());
            edCompAddr.setText(companies_s.get(i).getcompanyAddress());
            edCompCity.setText(companies_s.get(i).getcompanyCity());
            edCompPostCode.setText(companies_s.get(i).getcompanyPostcode());
            edCompPhone.setText(companies_s.get(i).getcompanyPhone());
            edContPName.setText(companies_s.get(i).getcontactpersonName());
            edContPPhone.setText(companies_s.get(i).getcontactpersonPhone());
            edContPEmail.setText(companies_s.get(i).getcontactpersonEmail());
            values.add(companies_s.get(i).getcomp_name());
        }
    }


    private void updater(){
        String _ed_comp_name = ed_comp_name.getText().toString();
        String _edCompAddr = edCompAddr.getText().toString();
        String _edCompCity = edCompCity.getText().toString();
        String _edCompPostCode = edCompPostCode.getText().toString();
        String _edCompPhone = edCompPhone.getText().toString();
        String _edContPName = edContPName.getText().toString();
        String _edContPPhone = edContPPhone.getText().toString();
        String _edContPEmail = edContPEmail.getText().toString();

        CompaniesRepo.update(ed_comp_id,_ed_comp_name, _edCompAddr, _edCompCity, _edCompPostCode, _edCompPhone,
                _edContPName, _edContPPhone, _edContPEmail);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent Uj_activity;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(CompaniesManMod.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(CompaniesManMod.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(CompaniesManMod.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cmmMod:
                updater();
                Intent Uj_activity = new Intent(CompaniesManMod.this, CompaniesMan.class);
                startActivity(Uj_activity);
                break;
        }
    }
}
