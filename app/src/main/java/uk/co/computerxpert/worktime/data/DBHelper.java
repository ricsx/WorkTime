package uk.co.computerxpert.worktime.data;

/**
 * Created by ricsx on 29/12/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import uk.co.computerxpert.worktime.App.App;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.Worktime;
import uk.co.computerxpert.worktime.data.repo.CompaniesRepo;
import uk.co.computerxpert.worktime.data.repo.WorktimeRepo;


public class DBHelper  extends SQLiteOpenHelper {

        //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =8;
    // Database Name
    private static final String DATABASE_NAME = "worktimeDB.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(CompaniesRepo.createTable());
        db.execSQL(WorktimeRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Companies.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Worktime.TABLE);
        onCreate(db);
    }

}

