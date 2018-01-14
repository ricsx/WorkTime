package uk.co.computerxpert.worktime.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.computerxpert.worktime.data.DatabaseManager;
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


    public static List<FullQuerys> getFullQuerys(String selectQuery){
        FullQuerys fullQuerys = new FullQuerys();
        List<FullQuerys> fullQuerys_s = new ArrayList<FullQuerys>();


        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Log.i(TAG_Ertek, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fullQuerys= new FullQuerys();
                fullQuerys.setcomp_id(cursor.getInt(cursor.getColumnIndex("comp_id")));
                fullQuerys.setcomp_name(cursor.getString(cursor.getColumnIndex("comp_name")));

                fullQuerys.setwage_id(cursor.getInt(cursor.getColumnIndex("wage_id")));
                fullQuerys.setwage_comp_id(cursor.getInt(cursor.getColumnIndex("wage_comp_id")));
                fullQuerys.setwage_startdate(cursor.getLong(cursor.getColumnIndex("wage_startdate")));
                fullQuerys.setwage_enddate(cursor.getLong(cursor.getColumnIndex("wage_enddate")));
                fullQuerys.setwage_val(cursor.getString(cursor.getColumnIndex("wage_val")));

                fullQuerys.setwt_id(cursor.getInt(cursor.getColumnIndex("wt_id")));
                fullQuerys.setwt_comp_id(cursor.getInt(cursor.getColumnIndex("wt_comp_id")));
                fullQuerys.setwt_startdate(cursor.getLong(cursor.getColumnIndex("wt_startdate")));
                fullQuerys.setwt_enddate(cursor.getLong(cursor.getColumnIndex("wt_enddate")));
                fullQuerys.setwt_rem(cursor.getString(cursor.getColumnIndex("wt_rem")));
                fullQuerys.setwt_week(cursor.getInt(cursor.getColumnIndex("wt_week")));
                fullQuerys.setwt_year(cursor.getInt(cursor.getColumnIndex("wt_year")));
                fullQuerys.setwt_hours(cursor.getString(cursor.getColumnIndex("wt_hours")));
                fullQuerys.setwt_salary(cursor.getString(cursor.getColumnIndex("wt_salary")));
                fullQuerys.setwt_stredate(cursor.getString(cursor.getColumnIndex("wt_stredate")));
                fullQuerys.setwt_stretime(cursor.getString(cursor.getColumnIndex("wt_stretime")));
                fullQuerys.setwt_strsdate(cursor.getString(cursor.getColumnIndex("wt_strsdate")));
                fullQuerys.setwt_strstime(cursor.getString(cursor.getColumnIndex("wt_strstime")));

                fullQuerys_s.add(fullQuerys);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return fullQuerys_s;
    }


}
