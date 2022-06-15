package com.example.todolistfragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.todolistfragments.databinding.FragmentCreateTaskBinding;

import java.util.Calendar;

/*
Assignment HW03
Patel_HW03.zip
Harsh Patel
 */

public class createTaskFragment extends Fragment {

    FragmentCreateTaskBinding binding;
    Calendar myCal = Calendar.getInstance();

    public createTaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static createTaskFragment newInstance(String param1, String param2) {
        createTaskFragment fragment = new createTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);

        getActivity().setTitle("Create Task");

        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        myCal.set(Calendar.YEAR, year);
                        myCal.set(Calendar.MONTH, month);
                        myCal.set(Calendar.DAY_OF_MONTH, day);
                        binding.textViewDateSelectedOut.setText((month + 1) + "/" + day + "/" + year);

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),date, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }


        });


        binding.buttonCancelCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelCreation();
            }
        });

        binding.buttonSubmitCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    RadioButton priority =  binding.radioGroupPriority.findViewById(binding.radioGroupPriority.getCheckedRadioButtonId());

                    listener.createTask(new Task(binding.editTextTaskName.getText().toString(), myCal, priority.getText().toString()));
                }
            }
        });

        return binding.getRoot();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextTaskName.getText().toString().isEmpty()){
            binding.editTextTaskName.setError("!");
            Toast.makeText(getContext(),"Invalid Task Name Entry!",Toast.LENGTH_LONG).show();
            good = false;
        }
        if (binding.textViewDateSelectedOut.getText().toString().isEmpty()){
            binding.textViewDateSelectedOut.setError("!");
            Toast.makeText(getContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            good =  false;
        }

        return good;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof ICreateTaskFragment){
            listener = (ICreateTaskFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IcreateTaskFragment");
        }
    }

    ICreateTaskFragment listener;
    public interface ICreateTaskFragment {
        void createTask(Task task);
        void cancelCreation();
    }
}