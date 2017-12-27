package uk.co.computerxpert.worktime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ricsx on 12/12/17.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "worktimeDB.db";
    private static String TBL_WORKTIME_NAME = "worktime";

    private static final String WT_ID = "wt_id";
    private static final String WT_COMP_ID = "wt_comp_id";
    private static final String WT_COMPNM = "wt_compnm";
    private static final String WT_STARTDATE = "wt_startdate";
    private static final String WT_STARTTIME = "wt_starttime";
    private static final String WT_ENDDATE = "wt_enddate";
    private static final String WT_ENDTIME = "wt_endtime";
    private static final String WT_REM = "wt_rem";
    private static final String WT_WEEK = "wt_week";

    private static final String TAG_Ertek = "TAG: ";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

     @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TBL_WORKTIME_NAME + "("
                + WT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WT_COMP_ID + " INTEGER, "
                + WT_COMPNM + " TEXT, "
                + WT_STARTDATE + " LONG, "
                + WT_ENDDATE + " LONG, "
                + WT_REM + " TEXT, "
                + WT_WEEK + " INTEGER"
                + ")";

        Log.i(TAG_Ertek,"SQL "+CREATE_PRODUCTS_TABLE);

        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_WORKTIME_NAME);
	        onCreate(db);
    }



    public ArrayList<String> printTableData(String table_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> rows = new ArrayList<String>();

        Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);

        if (cur.getCount() != 0) {
            cur.moveToFirst();

            do {
                String row_values = "";
                String row_2="";

                for (int i = 0; i < cur.getColumnCount(); i++) {
                    row_values = row_values + " || " + cur.getString(i);
                    rows.add(cur.getString(i));
                    row_2 = row_2+ " | " + rows;
                }
            } while (cur.moveToNext());
        }
        return rows;
    }



    public void addWtime(Wtime wtime) {

        Calendar dateTime = Calendar.getInstance();

        ContentValues values = new ContentValues();
        values.put(WT_COMP_ID, wtime.getwt_comp_id());
        values.put(WT_COMPNM, wtime.getwt_compnm());
        values.put(WT_STARTDATE, wtime.getWt_startdate());
        values.put(WT_ENDDATE, wtime.getWt_enddate());
        values.put(WT_REM, wtime.getWt_rem());

        int weekyear= dateTime.get(Calendar.WEEK_OF_YEAR);
        values.put(WT_WEEK, weekyear);
        SQLiteDatabase db = this.getWritableDatabase();

        Log.i(TAG_Ertek,"SQL "+values);

        db.insert(TBL_WORKTIME_NAME, null, values);
        db.close();
    }


     public Wtime findWtime(String query_select) {

        String query = query_select;
        ArrayList<String> rows = new ArrayList<String>();

	    SQLiteDatabase db = this.getWritableDatabase();

	    Cursor cursor = db.rawQuery(query, null);


	    rows = printTableData("worktime");
        String row2="";

	    for (int i = 0; i < rows.size(); i++) {
                    row2 = row2+ " | " + rows.get(i);
        }

        Log.d("LOG_TAG_RRRRRRRR", row2);

	    Wtime wtime = new Wtime();

	    if (cursor.moveToFirst()) {
    		cursor.moveToFirst();
    		wtime.setWt_id(Integer.parseInt(cursor.getString(0)));
            wtime.setWt_comp_id(Integer.parseInt(cursor.getString(1)));
            wtime.setWt_compnm(cursor.getString(2));
            wtime.setWt_startdate(Integer.parseInt(cursor.getString(3)));
            wtime.setWt_enddate(Integer.parseInt(cursor.getString(4)));
            wtime.setWt_rem(cursor.getString(5));
    		cursor.close();
    	} else {
    		wtime = null;
    	}
    	db.close();
    	return wtime;
    }


}
