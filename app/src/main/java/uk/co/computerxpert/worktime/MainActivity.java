package uk.co.computerxpert.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_Ertek = "TAG: ";
    private TextView mTextMessage;
    private int id = 1;
    private Intent Uj_activity;
    EditText res_WeekWorkday, res_WeekSalary, res_SalaryFactory;
    Button btn_lists;
    private int weekyear;


    final Calendar dateTime = Calendar.getInstance(Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        res_WeekWorkday = (EditText) findViewById(R.id.res_WeekWorkdaysBox);
        res_WeekSalary = (EditText) findViewById(R.id.res_WeekSalaryBox);
        res_SalaryFactory = (EditText) findViewById(R.id.res_WeekSalaryBox);
        btn_lists = (Button) findViewById(R.id.btn_lists);
        weekyear = dateTime.get(Calendar.WEEK_OF_YEAR);

        Log.i(TAG_Ertek, "datetime: " + dateTime.toString() + "\n");
        Log.i(TAG_Ertek, "weekyear: " + weekyear + "\n");


        // int aa = rownum("select count(*) from worktime where wt_week=50");
        // listWtime("Select * FROM worktime WHERE wt_compnm=\"a\"");
        listWtime("Select * FROM worktime WHERE wt_week=50");
    }



    public void listWtime(String nm) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Wtime wtime = dbHandler.findWtime(nm);


        if (wtime != null)

        {
            // res_Wt_idBox.setText(String.valueOf(wtime.getWt_id()));
            Log.i(TAG_Ertek, "wtime: " + wtime + "\n");
            res_WeekWorkday.setText(String.valueOf(wtime));

            res_SalaryFactory.setText(String.valueOf(wtime.getwt_compnm()));
        } else

        {
            res_SalaryFactory.setText("No Match Found");
        }

    }


/*
    public void lookupWtime(View view){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Wtime wtime = dbHandler.findWtime(res_Wt_compnmBox.getText().toString());

            if(wtime !=null)

        {
            res_Wt_idBox.setText(String.valueOf(wtime.getWt_id()));

            res_Wt_compnmBox.setText(String.valueOf(wtime.getwt_compnm()));
        } else

        {
            res_Wt_compnmBox.setText("No Match Found");
        }

    }
*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(MainActivity.this, MainActivity.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(MainActivity.this, Dashbrd.class);
                    Uj_activity.putExtra("sessid", id);
                    startActivity(Uj_activity);
                    // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

}