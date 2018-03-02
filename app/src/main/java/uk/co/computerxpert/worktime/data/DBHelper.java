package uk.co.computerxpert.worktime.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.data.model.Agencies;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.DefShifts;
import uk.co.computerxpert.worktime.data.model.Settings;
import uk.co.computerxpert.worktime.data.model.Wage;
import uk.co.computerxpert.worktime.data.model.Worktimes;
import uk.co.computerxpert.worktime.data.repo.AgenciesRepo;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.DefShiftsRepo;
import uk.co.computerxpert.worktime.data.repo.SettingsRepo;
import uk.co.computerxpert.worktime.data.repo.WageRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimesRepo;


public class DBHelper  extends SQLiteOpenHelper {

        //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =8;
    // Database Name
    private static final String DATABASE_NAME = "worktimeDB.db";
    private static final String TAG = DBHelper.class.getSimpleName();

    // boolean aa = isTableExists("DefShifts", false);

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(CompaniesRepo.createTable());
        db.execSQL(WorktimesRepo.createTable());
        db.execSQL(WageRepo.createTable());
        db.execSQL(AgenciesRepo.createTable());
        db.execSQL(DefShiftsRepo.createTable());
        db.execSQL(SettingsRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Companies.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Worktimes.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Wage.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Agencies.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DefShifts.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Settings.TABLE);
        onCreate(db);
    }

}

