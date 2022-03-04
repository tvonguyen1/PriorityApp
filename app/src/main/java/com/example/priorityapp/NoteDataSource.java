package com.example.priorityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

public class NoteDataSource {
    private SQLiteDatabase database;
    private NoteDBHelper dbHelper;

    public NoteDataSource(Context context) {
        dbHelper = new NoteDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertNote(Note n) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("notename", n.getNoteName());
            initialValues.put("notedetail", n.getNoteDetail());
            initialValues.put("priority", n.getNotePriority());

            didSucceed = database.insert("note", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateNote(Note n) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) n.getNoteID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("notename", n.getNoteName());
            updateValues.put("notedetail", n.getNoteDetail());
            updateValues.put("priority", n.getNotePriority());

        didSucceed = database.update("note", updateValues, "_id=" + rowId, null) > 0;
    } catch (Exception e) {
        //Do nothing -will return false if there is an exception
    }
        return didSucceed;
    }
    public boolean deleteNote(int noteID) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("note", "_id=" + noteID, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }
}
