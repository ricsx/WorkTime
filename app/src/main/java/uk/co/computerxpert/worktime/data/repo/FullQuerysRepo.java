package uk.co.computerxpert.worktime.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
import uk.co.computerxpert.worktime.data.model.Companies;
import uk.co.computerxpert.worktime.data.model.FullQuerys;

/**
 * Created by ricsx on 03/01/18.
 */

public class FullQuerysRepo {
    private FullQuerys fullQuerys;
    private static final String TAG_Ertek="TAG: ";

    public FullQuerysRepo(){

        fullQuerys= new FullQuerys();

    }


    public static List<FullQuerys> findFullQuerys(String selectQuery){
        FullQuerys fullQuerys = new FullQuerys();
        List<FullQuerys> fullQuerys_s = new ArrayList<FullQuerys>();


        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fullQuerys= new FullQuerys();
                fullQuerys.setwt_id(cursor.getInt(cursor.getColumnIndex("wt_id")));
                fullQuerys.setwt_comp_id(cursor.getInt(cursor.getColumnIndex("wt_comp_id")));
                fullQuerys.setcomp_id(cursor.getInt(cursor.getColumnIndex("comp_id")));
                fullQuerys.setcomp_name(cursor.getString(cursor.getColumnIndex("comp_name")));
                fullQuerys.setwt_startdate(cursor.getLong(cursor.getColumnIndex("wt_startdate")));
                fullQuerys.setwt_enddate(cursor.getLong(cursor.getColumnIndex("wt_enddate")));
                fullQuerys.setwage_val(cursor.getString(cursor.getColumnIndex("wage_val")));

                fullQuerys_s.add(fullQuerys);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return fullQuerys_s;
    }


}
