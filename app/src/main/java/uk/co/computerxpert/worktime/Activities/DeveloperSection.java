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
import android.widget.TextView;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;

public class DeveloperSection extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    private int id = 1;
    private Intent Uj_activity;
    EditText worktimeQueryBox;
    EditText wagesQueryBox;
    EditText companiesQueryBox;
    EditText dropTableNameBox;

    private static final String TAG_Ertek = "TAG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_section);

        wagesQueryBox = (EditText) findViewById(R.id.in_wagesQueryBox);
        companiesQueryBox = (EditText) findViewById(R.id.in_companiesQueryBox);
        worktimeQueryBox = (EditText) findViewById(R.id.in_worktimeQueryBox);
        dropTableNameBox = (EditText) findViewById(R.id.in_dropTableNameBox);
        Button wagesDel = (Button) findViewById(R.id.btn_wagesDBDel);
        Button companiesDel = (Button) findViewById(R.id.btn_compDBDel);
        Button worktimeDel = (Button) findViewById(R.id.btn_worktimeDBDel);
        Button droptable = (Button) findViewById(R.id.btn_dropTable);
        Button worktimeCreate = (Button) findViewById(R.id.btn_worktimeCreate);
        Button companiesCreate = (Button) findViewById(R.id.btn_companiesCreate);
        Button wagesCreate = (Button) findViewById(R.id.btn_wagesCreate);


        wagesDel.setOnClickListener(this);
        companiesDel.setOnClickListener(this);
        worktimeDel.setOnClickListener(this);
        droptable.setOnClickListener(this);
        worktimeCreate.setOnClickListener(this);
        companiesCreate.setOnClickListener(this);
        wagesCreate.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    public void wagesDBDel(){
        String var =  wagesQueryBox.getText().toString();
        WageRepo.delete(var);
    }

    public void companiesDBDel(){
        String var =  companiesQueryBox.getText().toString();
        CompaniesRepo.delete(var);
    }

    public void worktimeDBDel(){
        String var =  worktimeQueryBox.getText().toString();
        WorktimesRepo.delete(var);
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

    public void createCompanies(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(CompaniesRepo.createTable());
    }

    public void createWage(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL(WageRepo.createTable());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DeveloperSection.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DeveloperSection.this, Worktimes.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DeveloperSection.this, Setup.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wagesDBDel:
                wagesDBDel();
                break;
            case R.id.btn_compDBDel:
                companiesDBDel();
                break;
            case R.id.btn_worktimeDBDel:
                worktimeDBDel();
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
        }
    }
}
