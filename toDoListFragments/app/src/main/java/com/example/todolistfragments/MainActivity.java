package com.example.todolistfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.todolistfragments.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/*
Assignment HW03
Patel_HW03.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements ToDoListFragment.ItoDoListFragment,
        displayTaskFragment.IDisplayFragment, createTaskFragment.ICreateTaskFragment {

    ActivityMainBinding binding;
    ArrayList<Task> taskList = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutView, ToDoListFragment.newInstance(taskList), "toDo")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void createTaskView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutView, new createTaskFragment(), "create")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Tasks");
        String[] temp = new String[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            temp[i] = taskList.get(i).name;
        }


        builder.setItems(temp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutView, displayTaskFragment.newInstance(taskList.get(i)), "display")
                        .addToBackStack(null)
                        .commit();
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

    @Override
    public void cancelTaskView() {
        cancelCreation();
    }

    @Override
    public void deleteTask(Task task) {
        taskList.remove(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void createTask(Task task) {
        taskList.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCreation() {
        getSupportFragmentManager().popBackStack();
    }

}