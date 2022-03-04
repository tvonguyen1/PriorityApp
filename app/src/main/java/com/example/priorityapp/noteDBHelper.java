package com.example.priorityapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class noteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mynote.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_NOTE =
            "create table note (_id integer primary key autoincrement, "
                    + "notename text not null, notedetail text, "
                    + "priority);";

    public noteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(noteDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS note");
        onCreate(db);
    }
}
