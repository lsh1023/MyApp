package com.lsh.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日历
 */

public class MainActivity extends AppCompatActivity implements NewCalendar.NewCalendarListener {


    NewCalendar newCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newCalendar= (NewCalendar) findViewById(R.id.newCalendar);
        newCalendar.listener=this;

    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this, df.format(day), Toast.LENGTH_SHORT).show();
    }
}
