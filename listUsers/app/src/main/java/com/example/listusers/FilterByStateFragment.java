package com.example.listusers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.listusers.databinding.FragmentFilterByStateBinding;

import java.util.ArrayList;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */
public class FilterByStateFragment extends Fragment {
    FragmentFilterByStateBinding binding;
    ArrayList<String> statesArray;
    ArrayAdapter<String> adapter;

    public FilterByStateFragment() {
        // Required empty public constructor
    }

    public static FilterByStateFragment newInstance(String param1, String param2) {
        FilterByStateFragment fragment = new FilterByStateFragment();
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
        binding = FragmentFilterByStateBinding.inflate(inflater, container, false);
        getActivity().setTitle("Filter By State");

        statesArray = listener.getStatesArray();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, statesArray);
        binding.ListViewState.setAdapter(adapter);

        binding.ListViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.selectState(statesArray.get(i));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof IFilterByStateFragment){
            listener = (IFilterByStateFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IFilterByStateFragment");
        }
    }

    IFilterByStateFragment listener;
    public interface IFilterByStateFragment{
        ArrayList<String> getStatesArray();
        void selectState(String state);
    }
}