package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Worktime;

/**
 * Created by ricsx on 29/12/17.
 */






public class WorktimeRepo  {

    private Worktime worktime;

    public WorktimeRepo(){

        worktime= new Worktime();

    }


    public static String createTable(){
        return "CREATE TABLE " + Worktime.TABLE  + "("
                + Worktime.KEY_wt_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Worktime.KEY_wt_comp_id + " INTEGER, "
                + Worktime.KEY_wt_compnm + " TEXT, "
                + Worktime.KEY_wt_startdate + " LONG, "
                + Worktime.KEY_wt_enddate + " LONG, "
                + Worktime.KEY_wt_rem + " TEXT, "
                + Worktime.KEY_wt_week + " INTEGER )";
    }


    public static int insert(Worktime worktime) {
        int worktimeId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        // values.put(Worktime.KEY_wt_id, worktime.getwt_id());
        values.put(Worktime.KEY_wt_comp_id, worktime.getwt_comp_id());
        values.put(Worktime.KEY_wt_compnm, worktime.getwt_compnm());
        values.put(Worktime.KEY_wt_startdate, worktime.getwt_startdate());
        values.put(Worktime.KEY_wt_enddate, worktime.getwt_enddate());
        values.put(Worktime.KEY_wt_rem, worktime.getwt_rem());
        values.put(Worktime.KEY_wt_week, worktime.getwt_week());

        // Inserting Row
        worktimeId=(int) db.insert(Worktime.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return worktimeId;
    }


    public static void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Worktime.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}