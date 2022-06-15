package com.example.listusers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.listusers.databinding.FragmentUsersBinding;

import java.util.ArrayList;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */

public class UsersFragment extends Fragment {

    FragmentUsersBinding binding;
    UserAdapter adapter;
    ArrayList<DataServices.User> userArray;
    DataServices.User user;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
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
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        getActivity().setTitle("Users");

        userArray = listener.getUserArray();
        adapter = new UserAdapter(getContext(), R.layout.user_row_item, userArray);
        binding.ListViewUser.setAdapter(adapter);

        binding.buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.filter();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sort();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IUsersFragment){
            listener = (IUsersFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IUsersFragment");
        }
    }

    IUsersFragment listener;
    public interface IUsersFragment{
        void filter();
        void sort();
        ArrayList<DataServices.User> getUserArray();
    }
}