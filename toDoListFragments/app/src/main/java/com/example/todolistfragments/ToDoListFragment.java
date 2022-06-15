package com.example.todolistfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolistfragments.databinding.FragmentToDoListBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/*
Assignment HW03
Patel_HW03.zip
Harsh Patel
 */

public class ToDoListFragment extends Fragment {
    FragmentToDoListBinding binding;

    private static final String TASK_LIST = "taskList";

    private ArrayList<Task> mTaskList;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance(ArrayList<Task> taskList) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();

        args.putSerializable(TASK_LIST, taskList);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskList = (ArrayList<Task>) getArguments().getSerializable(TASK_LIST);
            //mTaskList = new ArrayList<Task>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentToDoListBinding.inflate(inflater, container, false);

        getActivity().setTitle("To Do List");

        // add if validation
        if (mTaskList.size() > 0){
            sortTask();
            binding.textViewTaskNameOut.setText(mTaskList.get(0).getName());
            binding.textViewDateOutCard.setText(String.format("%d/%d/%d",mTaskList.get(0).getDateDue().MONTH, mTaskList.get(0).getDateDue().get(Calendar.DAY_OF_MONTH), mTaskList.get(0).getDateDue().get(Calendar.YEAR)));
            binding.textViewPriorityOutCard.setText(mTaskList.get(0).getPriority());
            binding.textViewTaskNumOut.setText(String.format("You have %d tasks", mTaskList.size()));
        }else{
            binding.textViewTaskNameOut.setText("None");
            binding.textViewDateOutCard.setText("");
            binding.textViewPriorityOutCard.setText("");
            binding.textViewTaskNumOut.setText(String.format("You have %d tasks", 0));
        }

        binding.buttonViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectTask();
            }
        });

        binding.buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.createTaskView();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof ItoDoListFragment){
            listener = (ItoDoListFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ItoDoListFragment");
        }

    }

    ItoDoListFragment listener;

    public interface ItoDoListFragment{
        void createTaskView();
        void selectTask();

    }

    public void sortTask(){
        mTaskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.compareTo(t1);
            }
        });
    }
}