package com.example.posture_correction_jacket;

import android.provider.BaseColumns;

public class MemoContract {
    private MemoContract(){

    }

    public static class MemoEntry implements BaseColumns{
        public static final String TABLE_NAME = "memo";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_ANGLE = "angle";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_SUMMARY = "summary";
        public static final String COLUMN_NAME_BAG = "bag";

    }
}
