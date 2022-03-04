package com.example.priorityapp;

public class note {
    private int noteID;
    private String noteName;
    private String noteDetail;
    private String priority;

    public note() {
        noteID = -1;
    }

    public int getNoteID() {return noteID;}

    public void setNoteID(int id) {noteID = id;}

    public String getNoteName() {return noteName;}

    public void setNoteName(String name) {noteName = name;}

    public String getNoteDetail() {return noteDetail;}

    public void setNoteDetail(String detail) {noteDetail = detail;}

}
