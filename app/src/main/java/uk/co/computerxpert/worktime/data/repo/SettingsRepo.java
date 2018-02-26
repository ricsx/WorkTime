package uk.co.computerxpert.worktime.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Settings;

/**
 * Created by ricsx on 20/02/18.
 */

public class SettingsRepo {

    private Settings settings;
    private static final String TAG_Ertek="TAG: ";

    public SettingsRepo(){

        settings= new Settings();

    }


    public static String createTable(){
        return "CREATE TABLE " + Settings.TABLE  + "("
                + Settings.KEY_settings_id  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Settings.KEY_settings_name + " TEXT, "
                + Settings.KEY_settings_val + " TEXT "
                + ")";
    }


    public static int insert(Settings settings) {
        int settingsId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.KEY_settings_id, settings.get_settings_id());
        values.put(Settings.KEY_settings_name, settings.get_settings_name());
        values.put(Settings.KEY_settings_val, settings.get_settings_val());

        // Inserting Row
        settingsId=(int) db.insert(Settings.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return settingsId;
    }


    public static List<Settings> getSettings(String selectQuery){
        Settings settings = new Settings();
        List<Settings> settings_s = new ArrayList<Settings>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                settings= new Settings();
                settings.set_settings_id(cursor.getInt(cursor.getColumnIndex(Settings.KEY_settings_id)));
                settings.set_settings_name(cursor.getString(cursor.getColumnIndex(Settings.KEY_settings_name)));
                settings.set_settings_val(cursor.getString(cursor.getColumnIndex(Settings.KEY_settings_val)));
                settings_s.add(settings);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return settings_s;
    }


    public static void delete(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Settings.TABLE, query,null);
        DatabaseManager.getInstance().closeDatabase();
    }


    public static void update(int settings_id, String settings_name, String settings_val) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase(); //DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Settings.KEY_settings_name, settings_name);
        values.put(Settings.KEY_settings_val, settings_val);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update (Settings.TABLE, values, Settings.KEY_settings_id + "= ?",
                new String[] { String.valueOf(settings_id) });
        db.close(); // Closing database connection
        DatabaseManager.getInstance().closeDatabase();
    }
}