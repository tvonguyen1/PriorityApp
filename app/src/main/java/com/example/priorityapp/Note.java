package com.example.priorityapp;

public class Note {
    private int noteID;
    private String noteName;
    private String noteDetail;
    private String priority;

    public Note() {
        noteID = -1;
    }

    public int getNoteID() {return noteID;}

    public void setNoteID(int id) {noteID = id;}

    public String getNoteName() {return noteName;}

    public void setNoteName(String name) {noteName = name;}

    public String getNoteDetail() {return noteDetail;}

    public void setNoteDetail(String detail) {noteDetail = detail;}

    public String getNotePriority() {return priority;}

    public void setNotePriority(String priority) {priority = priority;}

}
