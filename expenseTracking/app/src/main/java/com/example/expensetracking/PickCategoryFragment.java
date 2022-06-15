package com.example.expensetracking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.expensetracking.databinding.FragmentPickCategoryBinding;

import java.util.ArrayList;


public class PickCategoryFragment extends Fragment {
    FragmentPickCategoryBinding binding;
    ArrayList<String> categoriesArray;
    ArrayAdapter<String> adapter;

    public PickCategoryFragment() {
        // Required empty public constructor
    }


    public static PickCategoryFragment newInstance(String param1, String param2) {
        PickCategoryFragment fragment = new PickCategoryFragment();
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
        binding = FragmentPickCategoryBinding.inflate(inflater, container, false);
        getActivity().setTitle("Pick Category");

        categoriesArray = listener.getCatArray();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, categoriesArray);
        binding.listViewCategories.setAdapter(adapter);

        binding.listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                listener.selectItem(categoriesArray.get(i).toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof IPickCategoryFragment){
            listener = (IPickCategoryFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IPickCategoryFragment");
        }
    }

    IPickCategoryFragment listener;
    public interface IPickCategoryFragment{
        ArrayList<String> getCatArray();
        void selectItem(String s);
    }
}