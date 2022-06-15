package com.example.ituneapps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.ituneapps.databinding.FragmentAppCategoriesBinding;

import java.util.ArrayList;

/*
Assignment InClass05
Group11_InClass05.zip
Harsh Patel
 */

public class AppCategoriesFragment extends Fragment {
    FragmentAppCategoriesBinding binding;
    ArrayAdapter<String> adapter;
    ArrayList<String> categoriesArray;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }


    public static AppCategoriesFragment newInstance() {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
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
        binding = FragmentAppCategoriesBinding.inflate(inflater, container, false);
        getActivity().setTitle("App Categories");

        categoriesArray = listener.getCatArray();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, categoriesArray);
        binding.listViewCategories.setAdapter(adapter);

        binding.listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.selectItem(categoriesArray.get(i));
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof IAppCategoriesFragment){
            listener = (IAppCategoriesFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IAppCategoriesFragment");
        }
    }

    IAppCategoriesFragment listener;
    public interface IAppCategoriesFragment{
        ArrayList<String> getCatArray();
        void selectItem(String s);
    }
}