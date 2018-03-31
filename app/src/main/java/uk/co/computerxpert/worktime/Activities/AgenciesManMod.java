package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;

public class AgenciesManMod extends AppCompatActivity implements View.OnClickListener{

    int ed_agency_id;
    EditText ed_agency_name;
    EditText edAgencyAddr;
    EditText edAgencyCity;
    EditText edAgencyPostCode;
    EditText edAgencyPhone;
    EditText edContPName;
    EditText edContPPhone;
    EditText edContPEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencies_man_mod);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        ed_agency_name = findViewById(R.id.upd_agencyNameBox);
        edAgencyAddr = findViewById(R.id.upd_agencyAddressBox);
        edAgencyCity = findViewById(R.id.upd_agencyCityBox);
        edAgencyPostCode = findViewById(R.id.upd_agencyPostcodeBox);
        edAgencyPhone = findViewById(R.id.upd_agencyPhoneBox);
        edContPName = findViewById(R.id.upd_agencyContPersNameBox);
        edContPPhone = findViewById(R.id.upd_agencyContPersPhoneBox);
        edContPEmail = findViewById(R.id.upd_agencyContPersEmailBox);

        Toolbar myToolbar = findViewById(R.id.agencies_man_mod_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Button btn_cmmMod = findViewById(R.id.btn_stp_agency_update);

        String fromAgencyanyID = getIntent().getStringExtra("agencyID");
        loadFormDefaults(fromAgencyanyID);

        btn_cmmMod.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
    }


    private void loadFormDefaults(String value){
        String selectQuery =  " SELECT * from Agencies WHERE " + Agencies.KEY_agency_id + " = \"" + value + "\"";
        List<Agencies> agencies_s= AgenciesRepo.getAgencies(selectQuery);
        // "values" array definition and loading
        ed_agency_id = Integer.parseInt(value);
        //noinspection MismatchedQueryAndUpdateOfCollection
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<agencies_s.size();i++){
            ed_agency_name.setText(agencies_s.get(i).getagency_name());
            edAgencyAddr.setText(agencies_s.get(i).getagencyAddress());
            edAgencyCity.setText(agencies_s.get(i).getagencyCity());
            edAgencyPostCode.setText(agencies_s.get(i).getagencyPostcode());
            edAgencyPhone.setText(agencies_s.get(i).getagencyPhone());
            edContPName.setText(agencies_s.get(i).getcontactpersonName());
            edContPPhone.setText(agencies_s.get(i).getcontactpersonPhone());
            edContPEmail.setText(agencies_s.get(i).getcontactpersonEmail());
            values.add(agencies_s.get(i).getagency_name());
        }
    }


    private void updater(){
        String _ed_agency_name = ed_agency_name.getText().toString();
        String _edAgencyAddr = edAgencyAddr.getText().toString();
        String _edAgencyCity = edAgencyCity.getText().toString();
        String _edAgencyPostCode = edAgencyPostCode.getText().toString();
        String _edAgencyPhone = edAgencyPhone.getText().toString();
        String _edContPName = edContPName.getText().toString();
        String _edContPPhone = edContPPhone.getText().toString();
        String _edContPEmail = edContPEmail.getText().toString();

        AgenciesRepo.update(ed_agency_id,_ed_agency_name, _edAgencyAddr, _edAgencyCity, _edAgencyPostCode, _edAgencyPhone,
                _edContPName, _edContPPhone, _edContPEmail);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent Uj_activity;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(AgenciesManMod.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(AgenciesManMod.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(AgenciesManMod.this, Setup.class);
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
                updater();
                Intent Uj_activity = new Intent(AgenciesManMod.this, AgenciesMan.class);
                startActivity(Uj_activity);
                break;
        }
    }
    
}
