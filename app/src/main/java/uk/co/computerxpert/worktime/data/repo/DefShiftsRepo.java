package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.DefShifts;

/**
 * Created by ricsx on 06/02/18.
 */

public class DefShiftsRepo {
    private DefShifts defShifts;
    private static final String TAG_Ertek="TAG: ";
    private DBHelper dbHelper;

    public DefShiftsRepo(){
        defShifts = new DefShifts();
    }


    public static String createTable(){
        return "CREATE TABLE " + DefShifts.TABLE  + "("
                + DefShifts.KEY_DS_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DefShifts.KEY_DS_Name + " TEXT, "
                + DefShifts.KEY_DS_comp_id + " INTEGER, "
                + DefShifts.KEY_DS_Starttime + " TEXT, "
                + DefShifts.KEY_DS_Endtime + " TEXT, "
                + DefShifts.KEY_DS_Unpbr + " INTEGER, "
                + DefShifts.KEY_DS_Agency_id + " INTEGER "
                + ")";
    }


    public static int insert(DefShifts defShifts) {
        int defShiftsId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DefShifts.KEY_DS_Name, defShifts.get_defsh_name());
        values.put(DefShifts.KEY_DS_comp_id, defShifts.get_defsh_comp_id());
        values.put(DefShifts.KEY_DS_Starttime, defShifts.get_defsh_starttime());
        values.put(DefShifts.KEY_DS_Endtime, defShifts.get_defsh_endtime());
        values.put(DefShifts.KEY_DS_Unpbr, defShifts.get_defsh_unpbr());
        values.put(DefShifts.KEY_DS_Agency_id, defShifts.get_defsh_agency_id());


        // Inserting Row
        defShiftsId=(int) db.insert(DefShifts.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return defShiftsId;
    }


    public static List<DefShifts> getDefShifts2(String selectQuery){
        DefShifts defShifts = new DefShifts();
        List<DefShifts> defShifts_s = new ArrayList<DefShifts>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                defShifts= new DefShifts();
                defShifts.set_defsh_id(cursor.getInt(cursor.getColumnIndex(DefShifts.KEY_DS_ID)));
                defShifts.set_defsh_name(cursor.getString(cursor.getColumnIndex(DefShifts.KEY_DS_Name)));
                defShifts_s.add(defShifts);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return defShifts_s;
    }


    public static List<DefShifts> getDefShifts(String selectQuery){
        DefShifts defShifts = new DefShifts();
        List<DefShifts> defShifts_s = new ArrayList<DefShifts>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                defShifts= new DefShifts();
                defShifts.set_defsh_id(cursor.getInt(cursor.getColumnIndex(DefShifts.KEY_DS_ID)));
                defShifts.set_defsh_name(cursor.getString(cursor.getColumnIndex(DefShifts.KEY_DS_Name)));
                defShifts.set_defsh_comp_id(cursor.getInt(cursor.getColumnIndex(DefShifts.KEY_DS_comp_id)));
                defShifts.set_defsh_starttime(cursor.getString(cursor.getColumnIndex(DefShifts.KEY_DS_Starttime)));
                defShifts.set_defsh_endtime(cursor.getString(cursor.getColumnIndex(DefShifts.KEY_DS_Endtime)));
                defShifts.set_defsh_unpbr(cursor.getInt(cursor.getColumnIndex(DefShifts.KEY_DS_Unpbr)));
                defShifts.set_defsh_agency_id(cursor.getInt(cursor.getColumnIndex(DefShifts.KEY_DS_Agency_id)));
                defShifts_s.add(defShifts);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return defShifts_s;
    }

    public static void update(int defsh_id, String defsh_name, int defsh_comp_id, String defsh_starttime,
                              String defsh_endtime, int defsh_unpbr, int defsh_agency_id) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DefShifts.KEY_DS_Name, defsh_name);
        values.put(DefShifts.KEY_DS_comp_id, defsh_comp_id);
        values.put(DefShifts.KEY_DS_Starttime, defsh_starttime);
        values.put(DefShifts.KEY_DS_Endtime, defsh_endtime);
        values.put(DefShifts.KEY_DS_Unpbr, defsh_unpbr);
        values.put(DefShifts.KEY_DS_Agency_id, defsh_agency_id);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update (DefShifts.TABLE, values, DefShifts.KEY_DS_ID + "= ?",
                new String[] { String.valueOf(defsh_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }


    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DefShifts.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}
