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
        public static final String COLUMN_NAME_LEFTPRESSURE = "leftPressure";
        public static final String COLUMN_NAME_RIGHTPRESSURE = "rightPressure";

    }
}
