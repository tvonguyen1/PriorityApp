package com.example.priorityapp;

import java.util.Calendar;

public class Note {
    private int noteID;
    private String noteName;
    private String noteDetail;
    private int priority;
    private Calendar date;
    private String dateString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate() {
        this.date = Calendar.getInstance();
    }

    public Note() {
        noteID = -1;
    }

    public int getNoteID() {return noteID;}

    public void setNoteID(int id) {noteID = id;}

    public String getNoteName() {return noteName;}

    public void setNoteName(String name) {noteName = name;}

    public String getNoteDetail() {return noteDetail;}

    public void setNoteDetail(String detail) {noteDetail = detail;}

}
