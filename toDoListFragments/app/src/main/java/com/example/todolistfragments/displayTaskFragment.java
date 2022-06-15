package com.example.todolistfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolistfragments.databinding.FragmentDisplayTaskBinding;

import java.util.Calendar;

/*
Assignment HW03
Patel_HW03.zip
Harsh Patel
 */

public class displayTaskFragment extends Fragment {

    FragmentDisplayTaskBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    private static final String TASK = "task";

    // TODO: Rename and change types of parameters
    private Task dTask;

    public displayTaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static displayTaskFragment newInstance(Task task) {
        displayTaskFragment fragment = new displayTaskFragment();
        Bundle args = new Bundle();

        args.putSerializable(TASK, task);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dTask = (Task) getArguments().getSerializable(TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisplayTaskBinding.inflate(inflater, container, false);

        getActivity().setTitle("Display Task");

        binding.textViewNameOutDisplay.setText(dTask.getName().toString());
        binding.textViewDateOut.setText(String.format("%d/%d/%d", dTask.getDateDue().MONTH, dTask.getDateDue().get(Calendar.DAY_OF_MONTH), dTask.getDateDue().get(Calendar.YEAR)));
        binding.textViewPriorityOutDisplay.setText(dTask.getPriority());

        binding.btnCancelDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelTaskView();
            }
        });

        binding.buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteTask(dTask);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof IDisplayFragment){
            listener = (IDisplayFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ILoginFragment");
        }
    }

    IDisplayFragment listener;
    public interface IDisplayFragment{
        void cancelTaskView();
        void deleteTask(Task task);
    }
}