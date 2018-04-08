package uk.co.computerxpert.worktime.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import uk.co.computerxpert.worktime.Common.Common;
import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.DatabaseManager;

import static uk.co.computerxpert.worktime.Common.Common.dateTime;
import static uk.co.computerxpert.worktime.Common.Common.formatDate;

public class DeleteWrongDays extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;
    Button _deleteDaysDate;
    EditText _inpDeleteDaysDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_wrong_days);

        Common.setStatusBarColor(this.getWindow(), this);

        _deleteDaysDate = findViewById(R.id.btn_deleteDaysDate);
        _inpDeleteDaysDate = findViewById(R.id.in_deleteDasyDate);
        Button _deleteDaysSend = findViewById(R.id.btn_deleteDaysSend);

        _deleteDaysDate.setOnClickListener(this);
        _deleteDaysSend.setOnClickListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        Toolbar myToolbar = findViewById(R.id.deleteWrongDays_top);
        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        _deleteDaysDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { updateDate(); }
        });

        _deleteDaysSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
                db.delete(uk.co.computerxpert.worktime.data.model.Worktimes.TABLE, "wt_strsdate = \""+_inpDeleteDaysDate.getText().toString()+"\"",null);
                DatabaseManager.getInstance().closeDatabase();
                Uj_activity = new Intent(DeleteWrongDays.this, Setup.class);
                startActivity(Uj_activity);
            }
        });
    }


    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            _deleteDaysDate.setText(formatDate.format(dateTime.getTime()));
            _inpDeleteDaysDate.setText(formatDate.format(dateTime.getTime()));
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(DeleteWrongDays.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(DeleteWrongDays.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(DeleteWrongDays.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {

    }
}
