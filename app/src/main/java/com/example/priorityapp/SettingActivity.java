package com.example.priorityapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initListButton();
        initNoteButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }


    private void initNoteButton() {
        ImageButton ibList = findViewById(R.id.note);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
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
                Intent intent = new Intent(SettingActivity.this, ListActivity.class);
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
                Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
    private void initSettings() {
        String sortBy = getSharedPreferences("NotesAppPreferences",
                Context.MODE_PRIVATE).getString("sortfield","date");
        String sortOrder = getSharedPreferences("NotesAppPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");
        RadioButton rbDate = findViewById(R.id.radioDate);
        RadioButton rbSubject = findViewById(R.id.radioSubject);
        RadioButton rbPriority = findViewById(R.id.radioPriority);
        if (sortBy.equalsIgnoreCase("notename")) {
            rbSubject.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("date")) {
            rbDate.setChecked(true);
        }
        else {
            rbPriority.setChecked(true);
        }
        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick () {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbDate = findViewById(R.id.radioDate);
                RadioButton rbSubject = findViewById(R.id.radioSubject);
                if (rbDate.isChecked()) {
                    getSharedPreferences("NotesAppPreferences",
                            Context.MODE_PRIVATE).edit().
                            putString("sortfield","date").apply();

                }
                else if (rbSubject.isChecked()) {
                    getSharedPreferences("NotesAppPreferences",
                            Context.MODE_PRIVATE).edit().
                            putString("sortfield","notename").apply();
                }
                else {
                    getSharedPreferences("NotesAppPreferences",
                            Context.MODE_PRIVATE).edit().
                            putString("sortfield","priority").apply();
                }

            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("NotesAppPreferences",
                            Context.MODE_PRIVATE).edit().
                            putString("sortorder", "ASC").apply();
                } else {
                    getSharedPreferences("NotesAppPreferences",
                            Context.MODE_PRIVATE).edit().
                            putString("sortorder", "DESC").apply();
                }
            }
        });
    }
}
