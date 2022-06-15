package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
/*
Homework 2
HW02.zip
Harsh Patel
 */

public class CreateTaskActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    //final static public String USER_KEY = "USER";
    EditText taskName;
    TextView dateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle("Create Task");

        taskName = findViewById(R.id.editTextNameIn);
        dateLabel = findViewById(R.id.textViewDateCreate);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateLabel.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

            }


        };

        // date selection
        findViewById(R.id.buttonSetDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog=  new DatePickerDialog(CreateTaskActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        RadioGroup priorityRadioGrp = findViewById(R.id.radioGrpPriority);

        // task creation button
        findViewById(R.id.buttonSubmitCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton priority = findViewById(priorityRadioGrp.getCheckedRadioButtonId());
                String priorityName = priority.getText().toString();

                if(checkAllField()){
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.USER_KEY, new Task(taskName.getText().toString(), myCalendar, priorityName));
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        findViewById(R.id.buttonCancelCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private boolean checkAllField() {
        boolean good = true;
        if(taskName.getText().toString().isEmpty()){
            taskName.setError("!");
            Toast.makeText(getApplicationContext(),"Invalid Task Name Entry!",Toast.LENGTH_LONG).show();
            good = false;
        }
        if (dateLabel.getText().toString().isEmpty()){
            dateLabel.setError("!");
            Toast.makeText(getApplicationContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            good =  false;
        }

        return good;
    }
}