package uk.co.computerxpert.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private int id = 1;
    private Intent Uj_activity;
    EditText res_Wt_idBox;
    EditText res_Wt_compnmBox;
    Button btn_leker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        res_Wt_idBox = (EditText) findViewById(R.id.res_Wt_idBox);
        res_Wt_compnmBox = (EditText) findViewById(R.id.res_Wt_compnmBox);
        btn_leker = (Button) findViewById(R.id.btn_leker);

    }


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
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

}
