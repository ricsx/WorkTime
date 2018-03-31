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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        ed_comp_name = findViewById(R.id.cmm_compNameBox);
        edCompAddr = findViewById(R.id.cmm_compAddressBox);
        edCompCity = findViewById(R.id.cmm_compCityBox);
        edCompPostCode = findViewById(R.id.cmm_compPostcodeBox);
        edCompPhone = findViewById(R.id.cmm_compPhoneBox);
        edContPName = findViewById(R.id.cmm_compContPersNameBox);
        edContPPhone = findViewById(R.id.cmm_compContPersPhoneBox);
        edContPEmail = findViewById(R.id.cmm_compContPersEmailBox);

        String fromCompanyID = getIntent().getStringExtra("companyID");
        Button btn_cmmMod = findViewById(R.id.btn_cmmMod);

        Toolbar myToolbar = findViewById(R.id.company_man_mod_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        // String firstRunFlag = "0";
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        loadFormDefaults(fromCompanyID);
        btn_cmmMod.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
    }


    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Companies WHERE " + Companies.KEY_comp_id + " = \"" + value + "\"";

        List<Companies> companies_s= CompaniesRepo.getCompanies(selectQuery);
        // "values" array definition and loading
        ed_comp_id = Integer.parseInt(value);
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> values = new ArrayList<>();
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
                    Uj_activity = new Intent(CompaniesManMod.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(CompaniesManMod.this, MainActivity.class);
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
