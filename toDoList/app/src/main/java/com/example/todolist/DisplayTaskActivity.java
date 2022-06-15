package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
/*
Homework 2
HW02.zip
Harsh Patel
 */

public class DisplayTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        setTitle("Display Task");

        TextView name = findViewById(R.id.textViewNameOut);
        TextView date = findViewById(R.id.textViewDateOut);
        TextView priority = findViewById(R.id.textViewPriorityOut);

        if(getIntent() != null && getIntent().getExtras() != null){
            Task curTask = (Task) getIntent().getSerializableExtra(MainActivity.USER_KEY);
            name.setText(curTask.getName());
            date.setText(String.format("%d/%d/%d",curTask.getDateDue().MONTH, curTask.getDateDue().get(Calendar.DAY_OF_MONTH), curTask.getDateDue().get(Calendar.YEAR)));
            priority.setText(curTask.getPriority());
        }

        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = getIntent().getIntExtra("INDEX", -1);

                Intent intent = new Intent();
                intent.putExtra(MainActivity.TASK_KEY, index);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}