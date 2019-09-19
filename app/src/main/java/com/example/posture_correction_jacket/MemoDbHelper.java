package com.example.posture_correction_jacket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;

    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "MEMO.db";
    public static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.COLUMN_NAME_DATE,
                    MemoContract.MemoEntry.COLUMN_NAME_TIME,
                    MemoContract.MemoEntry.COLUMN_NAME_ANGLE,
                    MemoContract.MemoEntry.COLUMN_NAME_LEFTPRESSURE,
                    MemoContract.MemoEntry.COLUMN_NAME_RIGHTPRESSURE);

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;


    public static MemoDbHelper getInstance(Context context){
        if (sInstance == null) {
            sInstance = new MemoDbHelper(context);
        }
        return sInstance;
    }


    public MemoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
