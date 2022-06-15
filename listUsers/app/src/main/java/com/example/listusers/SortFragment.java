package com.example.listusers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listusers.databinding.FragmentSortBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */
public class SortFragment extends Fragment implements SortRecyclerViewAdapter.ISortRecycler {

    FragmentSortBinding binding;
    LinearLayoutManager layoutManager;
    SortRecyclerViewAdapter adapter;
    ArrayList<String> options = new ArrayList<>();

    public SortFragment() {
        // Required empty public constructor
    }

   public static SortFragment newInstance(String param1, String param2) {
        SortFragment fragment = new SortFragment();
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
        binding = FragmentSortBinding.inflate(inflater, container, false);
        getActivity().setTitle("Sort");

        options.add("Age");
        options.add("Name");
        options.add("State");

        binding.recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        binding.recycleView.setLayoutManager(layoutManager);
        adapter = new SortRecyclerViewAdapter(options, this);
        binding.recycleView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof ISortFragment){
            listener = (ISortFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ISortFragment");
        }
    }

    ISortFragment listener;

    @Override
    public void ascend(String sortOpt) {
        listener.sortUsers(sortOpt, true);
    }

    @Override
    public void descend(String sortOpt) {
        listener.sortUsers(sortOpt, false);
    }

    public interface ISortFragment{
        void sortUsers(String sortBy, boolean ascend);
    }
}