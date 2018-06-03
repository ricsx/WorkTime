package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Persons;

public class PersonsRepo {

    public PersonsRepo(){

    }


    public static String createTable(){
        return "CREATE TABLE " + Persons.TABLE  + "("
                + Persons.KEY_persons_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Persons.KEY_persons_fname + " TEXT, "
                + Persons.KEY_persons_lname + " TEXT, "
                + Persons.KEY_personsAddress + " TEXT, "
                + Persons.KEY_personsCity + " TEXT, "
                + Persons.KEY_personsPostcode + " TEXT, "
                + Persons.KEY_personsPhone + " TEXT "
                + ")";
    }


    public static int insert(Persons Persons) {
        int personsId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Persons.KEY_persons_fname, Persons.getpersons_fname());
        values.put(Persons.KEY_persons_lname, Persons.getpersons_lname());
        values.put(Persons.KEY_personsAddress, Persons.getpersonsAddress());
        values.put(Persons.KEY_personsCity, Persons.getpersonsCity());
        values.put(Persons.KEY_personsPostcode, Persons.getpersonsPostcode());
        values.put(Persons.KEY_personsPhone, Persons.getpersonsPhone());
        // Inserting Row
        personsId=(int) db.insert(Persons.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return personsId;
    }


    public static int insertEmptyPersons() {
        int personsId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Persons.KEY_persons_id, 0);
        values.put(Persons.KEY_persons_fname, "-");
        values.put(Persons.KEY_persons_lname, "-");
        values.put(Persons.KEY_personsAddress, "");
        values.put(Persons.KEY_personsCity, "");
        values.put(Persons.KEY_personsPostcode, "");
        values.put(Persons.KEY_personsPhone, "");
        // Inserting Row
        personsId=(int) db.insert(Persons.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return personsId;
    }


    public static List<Persons> getPersons(String selectQuery){
        Persons Persons;
        List<Persons> Persons_s = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Persons= new Persons();
                Persons.setpersons_id(cursor.getInt(cursor.getColumnIndex(Persons.KEY_persons_id)));
                Persons.setpersons_fname(cursor.getString(cursor.getColumnIndex(Persons.KEY_persons_fname)));
                Persons.setpersons_lname(cursor.getString(cursor.getColumnIndex(Persons.KEY_persons_lname)));
                Persons.setpersonsAddress(cursor.getString(cursor.getColumnIndex(Persons.KEY_personsAddress)));
                Persons.setpersonsCity(cursor.getString(cursor.getColumnIndex(Persons.KEY_personsCity)));
                Persons.setpersonsPostcode(cursor.getString(cursor.getColumnIndex(Persons.KEY_personsPostcode)));
                Persons.setpersonsPhone(cursor.getString(cursor.getColumnIndex(Persons.KEY_personsPhone)));
                Persons_s.add(Persons);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return Persons_s;
    }

    public static void update(int persons_id, String persons_fname, String persons_lname, String personsAdd, String personsCity,
                              String personsPostc, String personsPhone, String contNm, String contPhone,
                              String contEmail) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Persons.KEY_persons_fname, persons_fname);
        values.put(Persons.KEY_persons_lname, persons_lname);
        values.put(Persons.KEY_personsAddress, personsAdd);
        values.put(Persons.KEY_personsCity, personsCity);
        values.put(Persons.KEY_personsPostcode, personsPostc);
        values.put(Persons.KEY_personsPhone, personsPhone);
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Persons.TABLE, values, Persons.KEY_persons_id + "= ?",
                new String[] { String.valueOf(persons_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }
}
