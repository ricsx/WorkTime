package uk.co.computerxpert.worktime.App;

/**
 * Created by ricsx on 29/12/17.
 */


import android.app.Application;
import android.content.Context;

import uk.co.computerxpert.worktime.data.DBHelper;
import uk.co.computerxpert.worktime.data.DatabaseManager;

public class  App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);

    }

    public static Context getContext(){
        return context;
    }

}
