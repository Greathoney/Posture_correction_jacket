package com.example.posture_correction_jacket;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class Menu4_Activity extends AppCompatActivity {

    TextView year_month;
    ListView dataList;
    private MemoAdapter mAdapter;
    Date pickedDate;


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


        final Date date = new Date();
        setPickedDate(date);
        Cursor cursor = getMemoCursor(date);
        mAdapter = new MemoAdapter(Menu4_Activity.this, cursor);
        dataList.setAdapter(mAdapter);




//        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
//        Event ev1 = new Event(Color.RED, 1568473200000L, "Some extra data that I want to store.");
//        compactCalendarView.addEvent(new Event(Color.RED, 1568473200000L, "Some extra data that I want to store."));

//
//
//        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event(Color.GREEN, 1568386800000L);
//        compactCalendarView.addEvent(ev2);

        SQLiteDatabase db = MemoDbHelper.getInstance(Menu4_Activity.this).getReadableDatabase();

        Cursor cursor1 = db.rawQuery("SELECT * from " + MemoContract.MemoEntry.TABLE_NAME  + " ORDER BY " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " DESC", null);
        cursor1.moveToFirst();


        String dateData;

        int color;

        int maxAngle = 0;
        int minAngle = 0;

        while (!cursor1.isAfterLast()) {
            dateData = cursor1.getString(1);
            maxAngle = 0;
            minAngle = 0;

            while (!cursor1.isAfterLast() && cursor1.getString(1).equals(dateData)){
                if (maxAngle < Integer.parseInt(cursor1.getString(3))){
                    maxAngle = Integer.parseInt(cursor1.getString(3));
                }

                else if (minAngle > Integer.parseInt(cursor1.getString(3))){
                    minAngle = Integer.parseInt(cursor1.getString(3));
                }

                cursor1.moveToNext();
            }

            DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date d = null;
            try {
                d = df.parse(dateData);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            compactCalendarView.addEvent(new Event(Color.rgb( 210 + 70 * minAngle, 210 - 35 * maxAngle + 35 * minAngle, 210 -70 * maxAngle), d.getTime(), ""));

//            if(Double.parseDouble(cursor1.getString(4)) > 10 && Double.parseDouble(cursor1.getString(5)) > 10){
//                Double sum = Double.parseDouble(cursor1.getString(4) + Double.parseDouble(cursor1.getString(5)));
//                Double sub = Double.parseDouble(cursor1.getString(4) + Double.parseDouble(cursor1.getString(5)));
//
//                compactCalendarView.addEvent(new Event(Color.rgb(255 - (int)Math.min(Math.round(sum), 255) + (int)Math.max(Math.round(sub), 0), 255 - (int)Math.min(Math.round(sum), 255), 255 - (int)Math.min(Math.round(sum), 255) + (int)Math.min(Math.round(sub), 0)), d.getTime(), ""));
//            }
//
//            else if (Double.parseDouble(cursor1.getString(4)) < 10 && Double.parseDouble(cursor1.getString(5)) > 10){
//                compactCalendarView.addEvent(new Event(Color.rgb( 255, 0, 0), d.getTime(), ""));
//            }
//
//            else if((Double.parseDouble(cursor1.getString(4)) > 10 && Double.parseDouble(cursor1.getString(5)) < 10)){
//                compactCalendarView.addEvent(new Event(Color.rgb( 255, 0, 0), d.getTime(), ""));
//            }
        }




        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
//        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);
        year_month.setText((compactCalendarView.getFirstDayOfCurrentMonth().getYear() + 1900) + "년  " + (compactCalendarView.getFirstDayOfCurrentMonth().getMonth() + 1) + "월");


        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
//                List<Event> events = compactCalendarView.getEvents(dateClicked);

                Cursor cursor = getMemoCursor(dateClicked);
                mAdapter = new MemoAdapter(Menu4_Activity.this, cursor);
                dataList.setAdapter(mAdapter);

                setPickedDate(dateClicked);

//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                year_month.setText((firstDayOfNewMonth.getYear() + 1900) + "년  " + (firstDayOfNewMonth.getMonth() + 1) + "월");
//                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long deleteId = l;
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu4_Activity.this);
                builder.setTitle("데이터 삭제");
                builder.setMessage("데이터를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = MemoDbHelper.getInstance(Menu4_Activity.this).getWritableDatabase();
                        db.delete(MemoContract.MemoEntry.TABLE_NAME,
                                MemoContract.MemoEntry._ID + " = " + deleteId, null);

                        Cursor cursor = getMemoCursor(getPickedDate());
                        mAdapter = new MemoAdapter(Menu4_Activity.this, cursor);
                        dataList.setAdapter(mAdapter);
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });
    }

    private Cursor getMemoCursor(Date dateClicked) {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        String checkString = "\'" + (dateClicked.getYear() + 1900) + "년 " + String.format("%02d", (dateClicked.getMonth() + 1)) + "월 " + String.format("%02d", (dateClicked.getDate())) + "일\'";

        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * from " + MemoContract.MemoEntry.TABLE_NAME +
                        " WHERE " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " = " + checkString +
                        " ORDER BY " + MemoContract.MemoEntry._ID + " DESC", null);
    }

    private Cursor getMaxDateCursor(){
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " from " + MemoContract.MemoEntry.TABLE_NAME  + " ORDER BY " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " DESC LIMIT 1", null);
    }

    private Cursor getMinDateCursor(){
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " from " + MemoContract.MemoEntry.TABLE_NAME  + " ORDER BY " + MemoContract.MemoEntry.COLUMN_NAME_DATE + " LIMIT 1", null);
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
            titleContent.setText("기울기 척도: " + cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_ANGLE)));
            titleContent.append(",  좌 감압: " + cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_LEFTPRESSURE)));
            titleContent.append(",  우 감압: " + cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_RIGHTPRESSURE)));

        }
    }

    private void setPickedDate(Date date){
        pickedDate = date;
    }

    private Date getPickedDate(){
        return pickedDate;
    }

}
