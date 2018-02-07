package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Worktimes;

/**
 * Created by ricsx on 29/12/17.
 */






public class WorktimesRepo {

    private Worktimes worktimes;
    private static final String TAG_Ertek = "TAG: ";


    public WorktimesRepo(){

        worktimes = new Worktimes();

    }


    public static String createTable(){

        String aa="CREATE TABLE " + Worktimes.TABLE  + " ( "
        + Worktimes.KEY_wt_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + Worktimes.KEY_wt_comp_id + " INTEGER, "
        + Worktimes.KEY_wt_startdate + " REAL, "
        + Worktimes.KEY_wt_enddate + " REAL, "
        + Worktimes.KEY_wt_strsdate + " TEXT, "
        + Worktimes.KEY_wt_strstime + " TEXT, "
        + Worktimes.KEY_wt_stredate + " TEXT, "
        + Worktimes.KEY_wt_stretime + " TEXT, "
        + Worktimes.KEY_wt_week + " INTEGER, "
        + Worktimes.KEY_wt_year + " INTEGER, "
        + Worktimes.KEY_wt_rem + " TEXT, "
        + Worktimes.KEY_wt_hours + " TEXT, "
        + Worktimes.KEY_wt_unpaidBreak + " INTEGER, "
        + Worktimes.KEY_wt_salary + " TEXT "
        + ")";

        return aa;
    }


    public static int insert(Worktimes worktimes) {
        int worktimeId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Worktimes.KEY_wt_comp_id, worktimes.getwt_comp_id());
        values.put(Worktimes.KEY_wt_startdate, worktimes.getwt_startdate());
        values.put(Worktimes.KEY_wt_enddate, worktimes.getwt_enddate());
        values.put(Worktimes.KEY_wt_rem, worktimes.getwt_rem());
        values.put(Worktimes.KEY_wt_week, worktimes.getwt_week());
        values.put(Worktimes.KEY_wt_year, worktimes.getwt_year());
        values.put(Worktimes.KEY_wt_strsdate, worktimes.getwt_strsdate());
        values.put(Worktimes.KEY_wt_stredate, worktimes.getwt_stredate());
        values.put(Worktimes.KEY_wt_hours, worktimes.getwt_hours());
        values.put(Worktimes.KEY_wt_salary, worktimes.getwt_salary());
        values.put(Worktimes.KEY_wt_strstime, worktimes.getwt_strstime());
        values.put(Worktimes.KEY_wt_stretime, worktimes.getwt_stretime());
        values.put(Worktimes.KEY_wt_unpaidBreak, worktimes.getwt_unpbr());
        // Inserting Row
        worktimeId=(int) db.insert(Worktimes.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return worktimeId;
    }


    public static List<Worktimes> findWorktime(String selectQuery){
        Worktimes worktimes = new Worktimes();
        List<Worktimes> worktimes_s = new ArrayList<Worktimes>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                worktimes = new Worktimes();
                worktimes.setwt_id(cursor.getInt(cursor.getColumnIndex(Worktimes.KEY_wt_id)));
                worktimes.setwt_comp_id(cursor.getInt(cursor.getColumnIndex(Worktimes.KEY_wt_comp_id)));
                worktimes.setwt_startdate(cursor.getLong(cursor.getColumnIndex(Worktimes.KEY_wt_startdate)));
                worktimes.setwt_enddate(cursor.getLong(cursor.getColumnIndex(Worktimes.KEY_wt_enddate)));
                worktimes.setwt_rem(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_rem)));
                worktimes.setwt_week(cursor.getInt(cursor.getColumnIndex(Worktimes.KEY_wt_week)));
                worktimes.setwt_year(cursor.getInt(cursor.getColumnIndex(Worktimes.KEY_wt_year)));
                worktimes.setwt_strsdate(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_strsdate)));
                worktimes.setwt_stredate(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_stredate)));
                worktimes.setwt_hours(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_hours)));
                worktimes.setwt_salary(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_salary)));
                worktimes.setwt_strstime(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_strstime)));
                worktimes.setwt_stretime(cursor.getString(cursor.getColumnIndex(Worktimes.KEY_wt_stretime)));
                worktimes.setwt_unpbr(cursor.getInt(cursor.getColumnIndex(Worktimes.KEY_wt_unpaidBreak)));
                worktimes_s.add(worktimes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return worktimes_s;
    }


    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Worktimes.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}