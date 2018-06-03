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
import uk.co.computerxpert.worktime.data.model.Persons;
import uk.co.computerxpert.worktime.data.repo.PersonsRepo;


public class PersonsMan extends AppCompatActivity implements View.OnClickListener {

    private Intent Uj_activity;
    EditText ed_persons_fname, ed_persons_lname, edAgencyAddr, edAgencyCity, edAgencyPostCode, edAgencyPhone, edContPName,
            edContPPhone, edContPEmail;
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_man);

        Common.setStatusBarColor(this.getWindow(), this);

        result= findViewById(R.id.results);
        ed_persons_fname = findViewById(R.id.cmm_personForeNameBox2);
        ed_persons_lname = findViewById(R.id.cmm_PersonLastNameBox2);
        edAgencyAddr = findViewById(R.id.cmm_personAddressBox2);
        edAgencyCity = findViewById(R.id.cmm_personCityBox2);
        edAgencyPostCode = findViewById(R.id.cmm_personPostcodeBox2);
        edAgencyPhone = findViewById(R.id.cmm_personPhoneBox2);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        String firstRunFlag = getIntent().getStringExtra("firstRunFlag");
        if(firstRunFlag == null){ firstRunFlag ="0"; }

        Button btn_stp_persons_send = findViewById(R.id.btn_stp_person_update2);

        Toolbar myToolbar = findViewById(R.id.persons_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        make_listview();
        btn_stp_persons_send.setOnClickListener(this);

        if(firstRunFlag.equals("0")) {
            navigation.setVisibility(View.VISIBLE);
        }else{
            navigation.setVisibility(View.GONE);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
    }



    public void persons_insert(){
        String persons_fname =  ed_persons_fname.getText().toString();
        String persons_lname =  ed_persons_lname.getText().toString();
        String persons_addrs = edAgencyAddr.getText().toString();
        String persons_city = edAgencyCity.getText().toString();
        String persons_postc = edAgencyPostCode.getText().toString();
        String persons_phone = edAgencyPhone.getText().toString();
        Persons persons = new Persons();
        persons.setpersons_fname(persons_fname);
        persons.setpersons_lname(persons_lname);
        persons.setpersonsAddress(persons_addrs);
        persons.setpersonsCity(persons_city);
        persons.setpersonsPostcode(persons_postc);
        persons.setpersonsPhone(persons_phone);
        PersonsRepo.insert(persons);
        ed_persons_fname.setText("");
        ed_persons_lname.setText("");
        edAgencyAddr.setText("");
        edAgencyCity.setText("");
        edAgencyPostCode.setText("");
        edAgencyPhone.setText("");
        finish();
    }


    private void make_listview(){
        String selectQuery =  " SELECT * from Persons";

        // PersonsRepo personsRepo = new PersonsRepo();
        List<Persons> persons_s= PersonsRepo.getPersons(selectQuery);

        // "values" array definition and loading
        ArrayList<String> values = new ArrayList<>();
        for(int i=0; i<persons_s.size();i++){ values.add(persons_s.get(i).getpersons_fname());  }

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
                String personsIDString = Integer.toString(getAgencyIDFromQuery("SELECT * FROM Persons WHERE persons_fname = \""+itemValue+"\""));
              //  Uj_activity = new Intent(PersonsMan.this, PersonsManMod.class);
                Uj_activity.putExtra("personsID", personsIDString);
                startActivity(Uj_activity);
            }
        });
    }




    private int getAgencyIDFromQuery(String query){
        int personsID=0;
        List<Persons> persons_s= PersonsRepo.getPersons(query);

        for(int i=0; i<persons_s.size();i++){ personsID = persons_s.get(i).getpersons_id(); }

        return personsID;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(PersonsMan.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(PersonsMan.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(PersonsMan.this, Setup.class);
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
                persons_insert();
                break;
        }
    }

}
