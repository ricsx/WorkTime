package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;

public class DeveloperSection extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;
    EditText sqlQueryBox,dropTableNameBox, defTblNameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_section);

        sqlQueryBox = findViewById(R.id.in_SQLQueryBox);
        dropTableNameBox = findViewById(R.id.in_dropTableNameBox);
        defTblNameBox = findViewById(R.id.in_defTblNameBox);

        Button execSql = findViewById(R.id.btn_execSql);
        Button droptable = findViewById(R.id.btn_dropTable);
        Button worktimeCreate = findViewById(R.id.btn_worktimeCreate);
        Button companiesCreate = findViewById(R.id.btn_companiesCreate);
        Button wagesCreate = findViewById(R.id.btn_wagesCreate);
        Button agenciesCreate = findViewById(R.id.btn_agenciesCreate);
        Button defShiftsCreate = findViewById(R.id.btn_defShiftsCreate);
        Button defDatas = findViewById(R.id.btn_defDatas);
        Button settingsCreate = findViewById(R.id.btn_SettingsCreate);

        execSql.setOnClickListener(this);
        droptable.setOnClickListener(this);
        worktimeCreate.setOnClickListener(this);
        companiesCreate.setOnClickListener(this);
        wagesCreate.setOnClickListener(this);
        agenciesCreate.setOnClickListener(this);
        defShiftsCreate.setOnClickListener(this);
        defDatas.setOnClickListener(this);
        settingsCreate.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    public void execSql(){
        String var =  sqlQueryBox.getText().toString();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(var);
    }

    public void dropTable(){
        String var =  dropTableNameBox.getText().toString();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL("DROP TABLE "+var);
    }

    public void createWorktime(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(WorktimesRepo.createTable());
    }

    public void createSettings(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(SettingsRepo.createTable());
    }

    public void createCompanies(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(CompaniesRepo.createTable());
    }

    public void createWage(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(WageRepo.createTable());
    }

    public void createAgencies(){
        try (SQLiteDatabase db = DatabaseManager.getInstance().openDatabase()) {
            db.execSQL(AgenciesRepo.createTable());
        }
    }

    public void createDefShifts(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(DefShiftsRepo.createTable());
    }

    public void fillDefDatas() {
        String var = defTblNameBox.getText().toString();
        if (var.equals("all")) { agenciesDef(); companiesDef(); defsshiftsDef(); wageDef(); worktimeDef();
        }if (var.equals("agencies")) { agenciesDef();
        }if (var.equals("companies")) { companiesDef();
        }if(var.equals("defshifts")) { defsshiftsDef();
        }if(var.equals("wage")) { wageDef();
        }if(var.equals("worktime")) { worktimeDef(); }
    }

    public void agenciesDef() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //db.execSQL(AgenciesRepo.createTable());
        String SqlQuery = "INSERT INTO `Agencies` (agency_id,agency_name,agency_addrs,agency_city,agency_postc,agency_phone,agency_cpname,agency_cpphone,agency_cpemail) " +
                "VALUES (1,'Köcsög Blue Arrow','12 Belgrade','Bellshill','ml42pe','+44 123 456 789','First Contact','+44 987 654 321','First@bluearrow.co.uk'), " +
                "(2,'SNJ','23 Somewher str','Bellshill','ML45BE','+44 321456789','A girl','+44 789654321','girl@snj.co.uk');";
        db.execSQL(SqlQuery);
    }

    public void companiesDef() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //db.execSQL(CompaniesRepo.createTable());
        String SqlQuery = "INSERT INTO `Companies` (comp_id,comp_name,comp_addrs,comp_city,comp_postc,comp_phone,comp_cpname,comp_cpphone,comp_cpemail,comp_agencyid) " +
                "VALUES (1,'Elsö cég','23, Itt str','Glasgow','G13ML','07850 250 726','elso contact person','01254 225 265','elso@elso.com',0), " +
                "(2,'Második cég','12, Somewhere ave.','Motherwell','ML142CV','01254 332 665','Második contact person','01222 333 444','masodik@masidok.co.uk',0), " +
                "(3,'Harmadik cég','3rd floor, KV Building, 54, Knockwille road','Bellshill','BL12VB','01365 549 987','Harmadik contact person','07234 567 896','harmadik@harmadik.com',0), " +
                "(4,'Negyedik c;g','','Bellshill','','01554 887 998','','','',0), (6,'6odik','','','','','','01445667889','',0);";
        db.execSQL(SqlQuery);
    }

    public void defsshiftsDef() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //db.execSQL(DefShiftsRepo.createTable());
        String SqlQuery = "INSERT INTO `DefShifts` (defsh_id,defsh_name,defsh_comp_id,defsh_starttime,defsh_endtime,defsh_unpbr,defsh_agency_id) " +
                "VALUES (1,'Elso shift',1,'06:00','14:30',30,1), " +
                "(2,'Második shift',1,'07:00','12:00',15,1)";
        db.execSQL(SqlQuery);
    }

    public void wageDef() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //db.execSQL(WageRepo.createTable());
        String SqlQuery = "INSERT INTO `Wage` (wage_id,wage_comp_id,wage_startdate,wage_enddate,wage_val) " +
                "VALUES (1,1,1512086400.0,1517356800.0,'7.50'), " +
                "(2,2,1509494400.0,1517356800.0,'8.85')";
        db.execSQL(SqlQuery);
    }

    public void worktimeDef() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //db.execSQL(WorktimesRepo.createTable());
        String SqlQuery= "INSERT INTO `Worktime` " +
                "(wt_id,wt_comp_id,wt_startdate,wt_enddate,wt_strsdate,wt_strstime,wt_stredate,wt_stretime,wt_week," +
                    "wt_year,wt_rem,wt_hours,wt_unpbr,wt_salary,wt_agency_id,wt_otwage) " +
                "VALUES " +
                "(1,1,1514786400.0,1514817000.0,'01 Jan 2018','06:00','01 Jan 2018','14:30',1,2018,'','8.5',30,'60.0',1,'0')," +
                "(2,1,1514872800.0,1514903400.0,'02 Jan 2018','06:00','02 Jan 2018','14:30',1,2018,'','8.5',30,'60.0',1,'0')," +
                "(3,1,1514966400.0,1514980800.0,'03 Jan 2018','08:00','03 Jan 2018','12:00',1,2018,'','4.0',15,'28.12',1,'0')," +
                "(4,1,1515045600.0,1515067200.0,'04 Jan 2018','06:00','04 Jan 2018','12:00',1,2018,'','6.0',15,'43.12',1,'0')," +
                "(5,1,1515132000.0,1515165300.0,'05 Jan 2018','06:00','05 Jan 2018','15:15',1,2018,'','9.25',30,'65.62',1,'0')," +
                "(6,1,1515391200.0,1515421800.0,'08 Jan 2018','06:00','08 Jan 2018','14:30',2,2018,'','8.5',30,'60.0',1,'0')," +
                "(7,1,1515477600.0,1515508200.0,'09 Jan 2018','06:00','09 Jan 2018','14:30',2,2018,'','8.5',30,'60.0',1,'0')," +
                "(8,1,1515564000.0,1515594600.0,'10 Jan 2018','06:00','10 Jan 2018','14:30',2,2018,'','8.5',30,'60.0',1,'0')";
        db.execSQL(SqlQuery);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DeveloperSection.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DeveloperSection.this, Worktimes.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DeveloperSection.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_execSql:
                execSql();
                break;
            case R.id.btn_dropTable:
                dropTable();
                break;
            case R.id.btn_worktimeCreate:
                createWorktime();
                break;
            case R.id.btn_companiesCreate:
                createCompanies();
                break;
            case R.id.btn_wagesCreate:
                createWage();
                break;
            case R.id.btn_agenciesCreate:
                createAgencies();
                break;
            case R.id.btn_defShiftsCreate:
                createDefShifts();
                break;
            case R.id.btn_defDatas:
                fillDefDatas();
                break;
            case R.id.btn_SettingsCreate:
                createSettings();
                break;
        }
    }
}
