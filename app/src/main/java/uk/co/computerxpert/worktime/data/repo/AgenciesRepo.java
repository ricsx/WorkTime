package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Agencies;

/**
 * Created by ricsx on 05/02/18.
 */

public class AgenciesRepo {
    private Agencies agencies;
    private static final String TAG_Ertek="TAG: ";
    private DBHelper dbHelper;

    public AgenciesRepo(){
        agencies = new Agencies();
        dbHelper = new DBHelper();
    }


    public static String createTable(){
        return "CREATE TABLE " + Agencies.TABLE  + "("
                + Agencies.KEY_agency_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Agencies.KEY_agency_name + " TEXT, "
                + Agencies.KEY_agencyAddress + " TEXT, "
                + Agencies.KEY_agencyCity + " TEXT, "
                + Agencies.KEY_agencyPostcode + " TEXT, "
                + Agencies.KEY_agencyPhone + " TEXT, "
                + Agencies.KEY_contactpersonName + " TEXT, "
                + Agencies.KEY_contactpersonPhone + " TEXT, "
                + Agencies.KEY_contactpersonEmail + " TEXT "
                + ")";
    }


    public static int insert(Agencies agencies) {
        int agencyId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Agencies.KEY_agency_name, agencies.getagency_name());
        values.put(Agencies.KEY_agencyAddress, agencies.getagencyAddress());
        values.put(Agencies.KEY_agencyCity, agencies.getagencyCity());
        values.put(Agencies.KEY_agencyPostcode, agencies.getagencyPostcode());
        values.put(Agencies.KEY_agencyPhone, agencies.getagencyPhone());
        values.put(Agencies.KEY_contactpersonName, agencies.getcontactpersonName());
        values.put(Agencies.KEY_contactpersonPhone, agencies.getcontactpersonPhone());
        values.put(Agencies.KEY_contactpersonEmail, agencies.getcontactpersonEmail());
        // Inserting Row
        agencyId=(int) db.insert(Agencies.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return agencyId;
    }


    public static List<Agencies> getAgencies2(String selectQuery){
        Agencies agencies = new Agencies();
        List<Agencies> agencies_s = new ArrayList<Agencies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                agencies= new Agencies();
                agencies.setagency_id(cursor.getInt(cursor.getColumnIndex(Agencies.KEY_agency_id)));
                agencies.setagency_name(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agency_name)));
                agencies_s.add(agencies);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return agencies_s;
    }


    public static List<Agencies> getAgencies(String selectQuery){
        Agencies agencies = new Agencies();
        List<Agencies> agencies_s = new ArrayList<Agencies>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                agencies= new Agencies();
                agencies.setagency_id(cursor.getInt(cursor.getColumnIndex(Agencies.KEY_agency_id)));
                agencies.setagency_name(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agency_name)));
                agencies.setagencyAddress(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agencyAddress)));
                agencies.setagencyCity(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agencyCity)));
                agencies.setagencyPostcode(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agencyPostcode)));
                agencies.setagencyPhone(cursor.getString(cursor.getColumnIndex(Agencies.KEY_agencyPhone)));
                agencies.setcontactpersonName(cursor.getString(cursor.getColumnIndex(Agencies.KEY_contactpersonName)));
                agencies.setcontactpersonPhone(cursor.getString(cursor.getColumnIndex(Agencies.KEY_contactpersonPhone)));
                agencies.setcontactpersonEmail(cursor.getString(cursor.getColumnIndex(Agencies.KEY_contactpersonEmail)));
                agencies_s.add(agencies);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return agencies_s;
    }

    public static void update(int agency_id, String agency_name, String agencyAdd, String agencyCity,
                              String agencyPostc, String agencyPhone, String contNm, String contPhone,
                              String contEmail) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Agencies.KEY_agency_name, agency_name);
        values.put(Agencies.KEY_agencyAddress, agencyAdd);
        values.put(Agencies.KEY_agencyCity, agencyCity);
        values.put(Agencies.KEY_agencyPostcode, agencyPostc);
        values.put(Agencies.KEY_agencyPhone, agencyPhone);
        values.put(Agencies.KEY_contactpersonName, contNm);
        values.put(Agencies.KEY_contactpersonPhone, contPhone);
        values.put(Agencies.KEY_contactpersonEmail, contEmail);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Agencies.TABLE, values, Agencies.KEY_agency_id + "= ?",
                new String[] { String.valueOf(agency_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }


    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Agencies.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }


}
