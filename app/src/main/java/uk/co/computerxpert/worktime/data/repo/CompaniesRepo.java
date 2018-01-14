package uk.co.computerxpert.worktime.data.repo;

/**
 * Created by ricsx on 29/12/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Companies;

import static uk.co.computerxpert.worktime.data.DBHelper.*;


public class CompaniesRepo  {

    private Companies companies;
    private static final String TAG_Ertek="TAG: ";
    private DBHelper dbHelper;

    public CompaniesRepo(){
        companies = new Companies();
        dbHelper = new DBHelper();
    }


    public static String createTable(){
        return "CREATE TABLE " + Companies.TABLE  + "("
                + Companies.KEY_comp_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Companies.KEY_comp_name + " TEXT, "
                + Companies.KEY_companyAddress + " TEXT, "
                + Companies.KEY_companyCity + " TEXT, "
                + Companies.KEY_companyPostcode + " TEXT, "
                + Companies.KEY_companyPhone + " TEXT, "
                + Companies.KEY_contactpersonName + " TEXT, "
                + Companies.KEY_contactpersonPhone + " TEXT, "
                + Companies.KEY_contactpersonEmail + " TEXT, "
                + Companies.KEY_companyAgencyID + " INTEGER "
                + ")";
    }


    public static int insert(Companies companies) {
        int compId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Companies.KEY_comp_name, companies.getcomp_name());
        values.put(Companies.KEY_companyAddress, companies.getcompanyAddress());
        values.put(Companies.KEY_companyCity, companies.getcompanyCity());
        values.put(Companies.KEY_companyPostcode, companies.getcompanyPostcode());
        values.put(Companies.KEY_companyPhone, companies.getcompanyPhone());
        values.put(Companies.KEY_contactpersonName, companies.getcontactpersonName());
        values.put(Companies.KEY_contactpersonPhone, companies.getcontactpersonPhone());
        values.put(Companies.KEY_contactpersonEmail, companies.getcontactpersonEmail());
        values.put(Companies.KEY_companyAgencyID, companies.getcompanyAgencyID());
        // Inserting Row
        compId=(int) db.insert(Companies.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return compId;
    }


    public static List<Companies> getCompanies2(String selectQuery){
        Companies companies = new Companies();
        List<Companies> companies_s = new ArrayList<Companies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
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


    public static List<Companies> getCompanies(String selectQuery){
        Companies companies = new Companies();
        List<Companies> companies_s = new ArrayList<Companies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                companies= new Companies();
                companies.setcomp_id(cursor.getInt(cursor.getColumnIndex(Companies.KEY_comp_id)));
                companies.setcomp_name(cursor.getString(cursor.getColumnIndex(Companies.KEY_comp_name)));
                companies.setcompanyAddress(cursor.getString(cursor.getColumnIndex(Companies.KEY_companyAddress)));
                companies.setcompanyCity(cursor.getString(cursor.getColumnIndex(Companies.KEY_companyCity)));
                companies.setcompanyPostcode(cursor.getString(cursor.getColumnIndex(Companies.KEY_companyPostcode)));
                companies.setcompanyPhone(cursor.getString(cursor.getColumnIndex(Companies.KEY_companyPhone)));
                companies.setcontactpersonName(cursor.getString(cursor.getColumnIndex(Companies.KEY_contactpersonName)));
                companies.setcontactpersonPhone(cursor.getString(cursor.getColumnIndex(Companies.KEY_contactpersonPhone)));
                companies.setcontactpersonEmail(cursor.getString(cursor.getColumnIndex(Companies.KEY_contactpersonEmail)));
                companies.setcompanyAgencyID(cursor.getInt(cursor.getColumnIndex(Companies.KEY_companyAgencyID)));

                companies_s.add(companies);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return companies_s;
    }

    public static void update(int comp_id, String comp_name, String compAdd, String compCity,
                                 String compPostc, String compPhone, String contNm, String contPhone,
                                 String contEmail) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Companies.KEY_comp_name, comp_name);
        values.put(Companies.KEY_companyAddress, compAdd);
        values.put(Companies.KEY_companyCity, compCity);
        values.put(Companies.KEY_companyPostcode, compPostc);
        values.put(Companies.KEY_companyPhone, compPhone);
        values.put(Companies.KEY_contactpersonName, contNm);
        values.put(Companies.KEY_contactpersonPhone, contPhone);
        values.put(Companies.KEY_contactpersonEmail, contEmail);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Companies.TABLE, values, Companies.KEY_comp_id + "= ?",
            new String[] { String.valueOf(comp_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }


    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Companies.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }

}