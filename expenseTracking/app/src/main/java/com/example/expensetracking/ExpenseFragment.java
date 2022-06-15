package com.example.expensetracking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetracking.databinding.FragmentExpenseBinding;

import java.util.ArrayList;
import java.util.Comparator;

public class ExpenseFragment extends Fragment implements ExpenseRecyclerViewAdapter.IExpenseRecycler{

    FragmentExpenseBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ExpenseRecyclerViewAdapter adapter;
    ArrayList<Expense> expenses;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    public static ExpenseFragment newInstance(String param1, String param2) {
        ExpenseFragment fragment = new ExpenseFragment();
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
        binding = FragmentExpenseBinding.inflate(inflater, container, false);
        getActivity().setTitle("Expenses");

        sortExpenses();
        expenses = listener.getExpenses();
        if (expenses.size() > 0){

            binding.textViewRecordsNumOut.setText(String.valueOf(expenses.size()));
        }
        binding.textViewTotalExpenseOut.setText(String.format("$%.2f", getSum(expenses)));

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExpenseRecyclerViewAdapter(expenses, this);
        recyclerView.setAdapter(adapter);

        binding.buttonAddExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addExp();
            }
        });
        binding.buttonExpSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.expSummary();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IExpenseFragment){
            listener = (IExpenseFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IExpenseFragment");
        }
    }

    double getSum(ArrayList<Expense> expList){
        if (expList.size() > 0){
            double sum = 0;
            for (Expense exp: expList) {
                sum += exp.amount;
            }
            return sum;
        }else{
            return 0;
        }
    }

    public void sortExpenses(){
        if (expenses != null){
            expenses.sort(new Comparator<Expense>() {
                @Override
                public int compare(Expense exp, Expense e1) {
                    return exp.compareTo(e1);
                }
            });
        }

    }

    IExpenseFragment listener;

    @Override
    public void delete(Expense exp) {
        listener.deleteExp(exp);
    }

    public interface IExpenseFragment{
        void addExp();
        void expSummary();
        ArrayList<Expense> getExpenses();
        void deleteExp(Expense exp);
    }
}