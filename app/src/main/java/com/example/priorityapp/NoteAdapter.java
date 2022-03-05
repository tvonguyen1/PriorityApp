package com.example.priorityapp;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter{
    private ArrayList<Note> noteData;
    static View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView textSubjectName;
        public TextView textDate;
        public TextView textPriority;
        public Button deleteButton;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubjectName = itemView.findViewById(R.id.textSubjectName);
            textDate = itemView.findViewById(R.id.textViewDate);
            textPriority = itemView.findViewById(R.id.textViewPriority);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getTextSubjectName() {
            return textSubjectName;
        }
        public TextView getTextDate() { return textDate;}
        public TextView getTextPriority() { return textPriority; }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public NoteAdapter(ArrayList<Note> arrayList, Context context) {
        noteData = arrayList;
        parentContext = context;
    }

    public static void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NoteViewHolder nvh = (NoteViewHolder) holder;
        nvh.getTextSubjectName().setText(noteData.get(position).getNoteName());
        nvh.getTextDate().setText(noteData.get(position).getDateString());
        if(noteData.get(position).getPriority()==2) {
            nvh.getTextPriority().setText("High");
            nvh.getTextPriority().setTextColor(Color.RED);
        } else if(noteData.get(position).getPriority()==1) {
            nvh.getTextPriority().setText("Medium");
        } else
            nvh.getTextPriority().setText("Low");
        //If the adapter is in delete mode, the delete button for each contact is set to be visible
        if (isDeleting) {
            nvh.getDeleteButton().setVisibility(View.VISIBLE);
            nvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else {
            nvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

    private void deleteItem(int position) {
        Note note = noteData.get(position);
        NoteDataSource ds = new NoteDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteNote(note.getNoteID());
            ds.close();
            if (didDelete) {
                noteData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {

        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }


}
