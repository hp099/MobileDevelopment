package com.example.expensetracking;

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

import com.example.expensetracking.databinding.FragmentAddExpenseBinding;
import com.example.expensetracking.databinding.FragmentExpenseBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AddExpenseFragment extends Fragment {

    FragmentAddExpenseBinding binding;
    Calendar myCal = Calendar.getInstance();
    String dateStr = "None";

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    public static AddExpenseFragment newInstance(String param1, String param2) {
        AddExpenseFragment fragment = new AddExpenseFragment();
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
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        getActivity().setTitle("Add New Expense");

        binding.textViewCategoryOut.setText(listener.getString());
        binding.textViewDateOut.setText(dateStr);

        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        myCal.set(Calendar.YEAR, year);
                        myCal.set(Calendar.MONTH, month);
                        myCal.set(Calendar.DAY_OF_MONTH, day);
                        dateStr = (month + 1) + "/" + day + "/" + year;
                        binding.textViewDateOut.setText(dateStr);
                        //binding.textViewDateOut.setText((month + 1) + "/" + day + "/" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),date, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.pickCat();
            }
        });

        binding.buttonSubmitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){

                    listener.addExpense(new Expense(binding.editTextName.getText().toString(),Double.valueOf(binding.editTextNumber.getText().toString()), myCal, binding.textViewCategoryOut.getText().toString()));
                }
            }
        });

        binding.buttonCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelAdd();
            }
        });


        return binding.getRoot();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextName.getText().toString().isEmpty()){
            binding.editTextName.setError("!");
            Toast.makeText(getContext(),"Invalid Expense Name Entry!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextNumber.getText().toString().isEmpty()){
            binding.editTextNumber.setError("!");
            Toast.makeText(getContext(),"Invalid Expense Amount Entry!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if (binding.textViewDateOut.getText().toString().equalsIgnoreCase("None")){
            binding.textViewDateOut.setError("!");
            Toast.makeText(getContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            good =  false;
        }

        if (binding.textViewCategoryOut.getText().toString().equalsIgnoreCase("None")){
            binding.textViewCategoryOut.setError("!");
            Toast.makeText(getContext(), "Category not selected!", Toast.LENGTH_SHORT).show();
            good =  false;
        }

        return good;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IAddExpenseFragment){
            listener = (IAddExpenseFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IAddExpenseFragment");
        }
    }

    IAddExpenseFragment listener;
    public interface IAddExpenseFragment{
        String getString();
        void pickCat();
        void addExpense(Expense exp);
        void cancelAdd();
    }
}