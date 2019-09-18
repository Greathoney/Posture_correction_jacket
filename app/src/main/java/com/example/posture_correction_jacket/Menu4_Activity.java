package com.example.posture_correction_jacket;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Menu4_Activity extends AppCompatActivity {

    TextView year_month;
    ListView dataList;
    private MemoAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu4);

        year_month = findViewById(R.id.year_month);
        dataList = findViewById(R.id.dataList);


        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

//        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
//        Event ev1 = new Event(Color.RED, 1568473200000L, "Some extra data that I want to store.");
//        compactCalendarView.addEvent(ev1);
//
//
//        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event(Color.GREEN, 1568386800000L);
//        compactCalendarView.addEvent(ev2);


        Cursor cursor = getMemoCursor();
        mAdapter = new MemoAdapter(this, cursor);
        dataList.setAdapter(mAdapter);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);
        year_month.setText((compactCalendarView.getFirstDayOfCurrentMonth().getYear() + 1900) + "년  " + (compactCalendarView.getFirstDayOfCurrentMonth().getMonth() + 1) + "월");


        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                year_month.setText((firstDayOfNewMonth.getYear() + 1900) + "년  " + (firstDayOfNewMonth.getMonth() + 1) + "월");
//                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }

    private Cursor getMemoCursor() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .query(MemoContract.MemoEntry.TABLE_NAME,
                        null, null, null, null, null, MemoContract.MemoEntry._ID + " DESC");
    }

    private static class MemoAdapter extends CursorAdapter {

        public MemoAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context)
                    .inflate(android.R.layout.two_line_list_item, viewGroup,false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_DATE)) + " ");
            titleText.append(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TIME)));
            TextView titleContent = view.findViewById(android.R.id.text2);
            titleContent.setText("기울기 점수: " + cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_ANGLE)));


        }
    }
}
