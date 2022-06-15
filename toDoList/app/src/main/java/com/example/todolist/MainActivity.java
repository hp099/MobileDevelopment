package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/*
Homework 2
HW02.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity {
    final static public int REQ_CODE = 100;
    final static public int REQ_CODE_TASK = 200;

    final static public String USER_KEY = "USER";
    final static public String TASK_KEY = "TASK";
    ArrayList<Task> numList;
    TextView taskNum;
    TextView taskName;
    TextView taskDueDate;
    TextView taskPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To Do List");

        numList = new ArrayList<Task>();
        taskNum = findViewById(R.id.TextViewNumTask);
        taskName = findViewById(R.id.textViewTaskDisplay);
        taskDueDate = findViewById(R.id.textViewDate);
        taskPriority = findViewById(R.id.textViewPriority);

        //creating/getting task
        findViewById(R.id.buttonCreateTask).setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
            startActivityForResult(intent, REQ_CODE);
        }));

        findViewById(R.id.buttonViewTasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Tasks");
                String[] temp = new String[numList.size()];
                for (int i = 0; i < numList.size(); i++) {
                    temp[i] = numList.get(i).name;
                }

                builder.setItems(temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                        intent.putExtra(USER_KEY, numList.get(i));
                        intent.putExtra("INDEX", i);
                        startActivityForResult(intent, REQ_CODE_TASK);
                    }
                });

                builder.setNegativeButton(
                                "CANCEL",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                builder.create().show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            if (requestCode == REQ_CODE){
                if(data != null && data.getExtras() != null && data.hasExtra(MainActivity.USER_KEY)){
                    Task taskIn = (Task) data.getSerializableExtra(MainActivity.USER_KEY);
                    numList.add(taskIn);

                    updatePage();

                }
            }else if(requestCode == REQ_CODE_TASK){
                if(data != null && data.getExtras() !=null && data.hasExtra(MainActivity.TASK_KEY)){
                    int index = (int) data.getIntExtra(MainActivity.TASK_KEY, -1);
                    numList.remove(index);
                    updatePage();
                }
            }

        }


    }

    public void updatePage(){

        if (numList.size() > 0){
            taskNum.setText(String.format("You have %d tasks", numList.size()));

            numList.sort(new Comparator<Task>() {
                @Override
                public int compare(Task task, Task t1) {
                    return task.compareTo(t1);
                }
            });

            taskName.setText(numList.get(0).getName().toString());
            taskDueDate.setText(String.format("%d/%d/%d",numList.get(0).getDateDue().MONTH, numList.get(0).getDateDue().get(Calendar.DAY_OF_MONTH), numList.get(0).getDateDue().get(Calendar.YEAR)));
            taskPriority.setText(numList.get(0).getPriority().toString());
        }else{
            taskNum.setText("You have 0 tasks");
            taskName.setText("None");
            taskDueDate.setText("");
            taskPriority.setText("");
        }

    }

}