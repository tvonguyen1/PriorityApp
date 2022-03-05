package com.example.priorityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
            initialValues.put("date", DateFormat.format("MM/dd/yyyy HH:mm:ss", n.getDate().getTimeInMillis()).toString());
            initialValues.put("priority", n.getPriority());

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
            updateValues.put("priority", n.getPriority());

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
    public ArrayList<Note> getNote(String sortField, String sortOrder) {
        ArrayList<Note> notes = new ArrayList<Note>();
        try {
            String query = "SELECT  * FROM note ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Note newNote;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newNote = new Note();
                newNote.setNoteID(cursor.getInt(0));
                newNote.setNoteName(cursor.getString(1));
                newNote.setNoteDetail(cursor.getString(2));
                newNote.setDateString(cursor.getString(3));
                newNote.setPriority(cursor.getInt(4));

                notes.add(newNote);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            notes = new ArrayList<Note>();
        }
        return notes;
    }

    public Note getSpecificNote(int noteId) {
        Note note = new Note();
        String query = "SELECT  * FROM note WHERE _id =" + noteId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            note.setNoteID(cursor.getInt(0));
            note.setNoteName(cursor.getString(1));
            note.setNoteDetail(cursor.getString(2));
            note.setDateString(cursor.getString(3));
            note.setPriority(cursor.getInt(4));
            cursor.close();
        }
        return note;
    }
    public int getLastNoteId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from note";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
}
