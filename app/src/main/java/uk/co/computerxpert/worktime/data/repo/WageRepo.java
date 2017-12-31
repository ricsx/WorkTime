package uk.co.computerxpert.worktime.data.repo;

/**
 * Created by ricsx on 30/12/17.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Wage;


public class WageRepo {

    private Wage wage;
    private static final String TAG_Ertek="TAG: ";

    public WageRepo(){

        wage= new Wage();

    }


    public static String createTable(){
        return "CREATE TABLE " + Wage.TABLE  + "("
                + Wage.KEY_wage_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Wage.KEY_wage_comp_id + " INTEGER, "
                + Wage.KEY_wage_startdate + " LONG, "
                + Wage.KEY_wage_enddate + " LONG, "
                + Wage.KEY_wage_val + " TEXT )";
    }


    public static int insert(Wage wage) {
        int compId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Wage.KEY_wage_comp_id, wage.getwage_comp_id());
        values.put(Wage.KEY_wage_startdate, wage.getwage_startdate());
        values.put(Wage.KEY_wage_enddate, wage.getwage_enddate());
        values.put(Wage.KEY_wage_val, wage.getwage_val());

        // Inserting Row
        compId=(int) db.insert(Wage.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return compId;
    }



    public List<Wage> getWage(){
        Wage wage = new Wage();
        List<Wage> wage_s = new ArrayList<Wage>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT Wage." + Wage.KEY_wage_id
                + ", Wage." + Wage.KEY_wage_comp_id
                + ", Wage." + Wage.KEY_wage_startdate
                + ", Wage." + Wage.KEY_wage_enddate
                + ", Wage." + Wage.KEY_wage_val
                + " FROM " + Wage.TABLE
                ;

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wage= new Wage();
                wage.setwage_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_id)));
                wage.setwage_comp_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_comp_id)));
                wage.setwage_startdate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_startdate)));
                wage.setwage_enddate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_enddate)));
                wage.setwage_val(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_val)));

                wage_s.add(wage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return wage_s;

    }


    public static List<Wage> findWage(String selectQuery){
        Wage wage = new Wage();
        List<Wage> wage_s = new ArrayList<Wage>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wage= new Wage();
                wage.setwage_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_id)));
                wage.setwage_comp_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_comp_id)));
                wage.setwage_startdate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_startdate)));
                wage.setwage_enddate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_enddate)));
                wage.setwage_val(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_val)));
                wage_s.add(wage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return wage_s;
    }



    public static List<Wage> RelfindWage(String selectQuery){
        Wage wage = new Wage();
        List<Wage> wage_s = new ArrayList<Wage>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Log.i(TAG_Ertek, "vizsgalt"+ selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wage= new Wage();
                wage.setwage_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_id)));
                wage.setwage_comp_id(cursor.getInt(cursor.getColumnIndex(Wage.KEY_wage_comp_id)));
                wage.setwage_startdate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_startdate)));
                wage.setwage_enddate(cursor.getLong(cursor.getColumnIndex(Wage.KEY_wage_enddate)));
                wage.setwage_val(cursor.getString(cursor.getColumnIndex(Wage.KEY_wage_val)));
                wage.setcomp_name(cursor.getString(cursor.getColumnIndex("comp_name")));
                wage_s.add(wage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return wage_s;
    }


    public static void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Wage.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}