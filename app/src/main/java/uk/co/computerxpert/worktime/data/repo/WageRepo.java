package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Wage;


public class WageRepo {

    public WageRepo(){
    }


    public static String createTable(){
        return "CREATE TABLE " + Wage.TABLE  + "("
                + Wage.KEY_wage_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Wage.KEY_wage_comp_id + " INTEGER, "
                + Wage.KEY_wage_startdate + " REAL, "
                + Wage.KEY_wage_strstdate + " TEXT, "
                + Wage.KEY_wage_val + " TEXT "
                + ")";
    }


    public static int insert(Wage wage) {
        int compId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Wage.KEY_wage_comp_id, wage.getwage_comp_id());
        values.put(Wage.KEY_wage_startdate, wage.getwage_startdate());
        values.put(Wage.KEY_wage_strstdate, wage.getwage_strstdate());
        values.put(Wage.KEY_wage_val, wage.getwage_val());

        // Inserting Row
        compId=(int) db.insert(Wage.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return compId;
    }



    public static List<Wage> getWage(String selectQuery){
        Wage wage;
        List<Wage> wage_s = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wage= new Wage();
                wage.setwage_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_id)));
                wage.setwage_comp_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_comp_id)));
                wage.setwage_startdate(cursor.getDouble(cursor.getColumnIndex(Wage.KEY_wage_startdate)));
                wage.setwage_strstdate(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_strstdate)));
                wage.setwage_val(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_val)));

                wage_s.add(wage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return wage_s;
    }


    public static List<Wage> relGetWage(String selectQuery){
        Wage wage;
        List<Wage> wage_s = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wage= new Wage();
                wage.setwage_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_id)));
                wage.setwage_comp_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_comp_id)));
                wage.setwage_startdate(cursor.getDouble(cursor.getColumnIndex(Wage.KEY_wage_startdate)));
                wage.setwage_strstdate(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_strstdate)));
                wage.setwage_val(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_val)));
                wage.setcomp_name(cursor.getString(cursor.getColumnIndex("comp_name")));

                wage_s.add(wage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return wage_s;
    }


    public static void update(int wage_id, int wage_comp_id, Double wage_startdate, String wage_strstdate,
                              String wage_val) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Wage.KEY_wage_comp_id, wage_comp_id);
        values.put(Wage.KEY_wage_startdate, wage_startdate);
        values.put(Wage.KEY_wage_strstdate, wage_strstdate);
        values.put(Wage.KEY_wage_val, wage_val);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update (Wage.TABLE, values, Wage.KEY_wage_id + "= ?",
                new String[] { String.valueOf(wage_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }
}