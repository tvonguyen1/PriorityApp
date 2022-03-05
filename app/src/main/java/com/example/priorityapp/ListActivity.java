package com.example.priorityapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView noteList;
    NoteAdapter noteAdapter;

    ArrayList<Note> notes;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int noteId = notes.get(position).getNoteID();
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        NoteAdapter.setOnItemClickListener(onItemClickListener);


        initNoteButton();
        initListButton();
        initSettingButton();

        initDeleteSwitch();

    }

    @Override
    //lifecycle-aware component tied to the activity's lifecycle will receive in this state
    //enable any functionality that needs to run while the component is visible and in the foreground
    //onCreate destroy old activity to start new one

    public void onResume() {
        super.onResume();
        //got from setting

        String sortBy = getSharedPreferences("NotesAppPreferences", Context.MODE_PRIVATE).getString("sortfield", "notename");
        String sortOrder = getSharedPreferences("NotesAppPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        NoteDataSource ds = new NoteDataSource(this);
        try {
            ds.open();
            notes = ds.getNote(sortBy, sortOrder);
            ds.close();
            //if ContactList is empty open MainActivity first
            //Check Manifest on how to prompt the app to open ContactList first
            if (notes.size() > 0) {
                noteList = findViewById(R.id.rvLists);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                noteList.setLayoutManager(layoutManager);
                noteAdapter = new NoteAdapter(notes, this);
                noteAdapter.setOnItemClickListener(onItemClickListener);
                noteList.setAdapter(noteAdapter);
            }


            else {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving notes", Toast.LENGTH_LONG).show();
        }

    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                noteAdapter.setDelete(status);
                noteAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initNoteButton() {
        ImageButton ibList = findViewById(R.id.note);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.list);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void initSettingButton() {
        ImageButton ibList = findViewById(R.id.setting);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
