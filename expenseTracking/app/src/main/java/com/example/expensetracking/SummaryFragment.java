package com.example.expensetracking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetracking.databinding.FragmentSummaryBinding;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class SummaryFragment extends Fragment {
    ArrayList<Expense> expenses;

    FragmentSummaryBinding binding;
    public SummaryFragment() {
        // Required empty public constructor
    }


    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
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
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        getActivity().setTitle("Expenses Summary");

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof ISummaryFragment){
            listener = (ISummaryFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ISummaryFragment");
        }
    }

    HashMap<YearMonth, Double> summarize(){
        HashMap<YearMonth, Double> summary = new HashMap<>();
        for (Expense exp: expenses) {
            if (summary.containsKey(YearMonth.from(exp.date.toInstant()))){
                summary.put(YearMonth.from(exp.date.toInstant()), exp.amount + summary.get(YearMonth.from(exp.date.toInstant())));
            }else{
                summary.put(YearMonth.from(exp.date.toInstant()), exp.amount);
            }
        }


        return summary;
    }

    ISummaryFragment listener;
    public interface ISummaryFragment{
        ArrayList<Expense> getSumArray();
    }
}