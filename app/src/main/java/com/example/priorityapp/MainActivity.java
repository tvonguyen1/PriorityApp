package com.example.priorityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class MainActivity extends AppCompatActivity {
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initSettingButton();

        initToggleButton();
        initPriorityChange();
        initTextChangedEvents();
        initSaveButton();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initNote(extras.getInt("noteId"));
        }
        else {
            currentNote = new Note();
        }
    }


    private void initNoteButton() {
        ImageButton ibList = findViewById(R.id.note);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
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
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void initToggleButton() {
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtoneEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }

        });
    }

    private void setForEditing(boolean enabled) {
        EditText editSubject = findViewById(R.id.editSubject);
        EditText editNote = findViewById(R.id.editNote);
        Button buttonSave = findViewById(R.id.buttonSave);
        RadioButton rbHigh = findViewById(R.id.radioHigh);
        RadioButton rbMedium = findViewById(R.id.radioMedium);
        RadioButton rbLow = findViewById(R.id.radioLow);

        editSubject.setEnabled(enabled);
        editNote.setEnabled(enabled);
        buttonSave.setEnabled(enabled);
        rbMedium.setEnabled(enabled);
        rbHigh.setEnabled(enabled);
        rbLow.setEnabled(enabled);

    }
    private void initTextChangedEvents() {
        final EditText etSubject = findViewById(R.id.editSubject);
        etSubject.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentNote.setNoteName(etSubject.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etNote = findViewById(R.id.editNote);
        etNote.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentNote.setNoteDetail(etNote.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });
    }

    private void initPriorityChange() {
        RadioGroup rgPriority = findViewById(R.id.radioGroupPriority);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final RadioButton rbHigh = findViewById(R.id.radioHigh);
                final RadioButton rbMedium = findViewById(R.id.radioMedium);
                final RadioButton rbLow = findViewById(R.id.radioLow);
                if (rbHigh.isChecked()) {
                    currentNote.setPriority(2);
                    System.out.println(currentNote.getPriority());

                } else if (rbMedium.isChecked()) {
                    currentNote.setPriority(1);
                } else{
                    currentNote.setPriority(0);

                }
            }
        });
    }
    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                hideKeyboard();
                NoteDataSource ds = new NoteDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentNote.getNoteID() == -1) {
                        currentNote.setDate();
                        wasSuccessful = ds.insertNote(currentNote);
                        if (wasSuccessful) {
                            int newId = ds.getLastNoteId();
                            currentNote.setNoteID(newId);
                        }

                    } else {
                        wasSuccessful = ds.updateNote(currentNote);
                    }
                    ds.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }

                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButtoneEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editSubject = findViewById(R.id.editSubject);
        imm.hideSoftInputFromWindow(editSubject.getWindowToken(), 0);
        EditText editNote = findViewById(R.id.editNote);
        imm.hideSoftInputFromWindow(editNote.getWindowToken(), 0);
        RadioGroup radioGroup = findViewById(R.id.radioGroupPriority);
        imm.hideSoftInputFromWindow(radioGroup.getWindowToken(), 0);
    }

    private void initNote(int id) {

        NoteDataSource ds = new NoteDataSource(MainActivity.this);
        try {
            ds.open();
            currentNote = ds.getSpecificNote(id);
            ds.close();
        } catch (Exception e) {
            Toast.makeText(this, "Load Note Failed", Toast.LENGTH_LONG).show();
        }

        EditText editSubject = findViewById(R.id.editSubject);
        EditText editNote = findViewById(R.id.editNote);
        final RadioButton rbHigh = findViewById(R.id.radioHigh);
        final RadioButton rbMedium = findViewById(R.id.radioMedium);
        final RadioButton rbLow = findViewById(R.id.radioLow);


        editSubject.setText(currentNote.getNoteName());
        editNote.setText(currentNote.getNoteDetail());

        if (currentNote.getPriority()==2) {
            rbHigh.setChecked(true);
        } else if (currentNote.getPriority()==1) {
            rbMedium.setChecked(true);
        } else {
            rbLow.setChecked(true);
        }

    }
}