package uk.co.computerxpert.worktime.data.repo;

/**
 * Created by ricsx on 29/12/17.
 */


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Companies;


public class CompaniesRepo  {

    private Companies companies;
    private static final String TAG_Ertek="TAG: ";

    public CompaniesRepo(){

        companies= new Companies();

    }


    public static String createTable(){
        return "CREATE TABLE " + Companies.TABLE  + "("
                + Companies.KEY_comp_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Companies.KEY_comp_name + " TEXT )";
    }


    public static int insert(Companies companies) {
        int compId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Companies.KEY_comp_id, companies.getcomp_id());
        values.put(Companies.KEY_comp_name, companies.getcomp_name());

        // Inserting Row
        compId=(int) db.insert(Companies.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return compId;
    }



/*    public List<Companies> getCompanies(){
        Companies companies = new Companies();
        List<Companies> companies_s = new ArrayList<Companies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT Companies." + Companies.KEY_comp_id
                + ", Companies." + Companies.KEY_comp_name
                + " FROM " + Companies.TABLE
                ;

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                companies= new Companies();
                companies.setcomp_id(cursor.getInt(cursor.getColumnIndex(Companies.KEY_comp_id)));
                companies.setcomp_name(cursor.getString(cursor.getColumnIndex(Companies.KEY_comp_name)));

                companies_s.add(companies);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return companies_s;

    }
*/

    public static List<Companies> findCompanies(String selectQuery){
        Companies companies = new Companies();
        List<Companies> companies_s = new ArrayList<Companies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                companies= new Companies();
                companies.setcomp_id(cursor.getInt(cursor.getColumnIndex(Companies.KEY_comp_id)));
                companies.setcomp_name(cursor.getString(cursor.getColumnIndex(Companies.KEY_comp_name)));

                companies_s.add(companies);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return companies_s;
    }



    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Companies.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}