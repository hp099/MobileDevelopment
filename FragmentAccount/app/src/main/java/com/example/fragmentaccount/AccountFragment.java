package com.example.fragmentaccount;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentaccount.databinding.FragmentAccountBinding;

/*
In Class Assignment 4
Group11_InClass04.zip
Harsh Patel
 */

public class AccountFragment extends Fragment {
    private static final String USER_NAME = "userName";
    private String userName;
    FragmentAccountBinding binding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String name) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.userName = getArguments().getString(USER_NAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        binding.textViewName.setText(userName);

        binding.buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editProfile();
            }
        });

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.logOut();
            }
        });

        return binding.getRoot();
    }

    public void updateName(String name){
        binding.textViewName.setText(name);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof AccountFragment.IAccountFragment){
            listener = (AccountFragment.IAccountFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IAccountFragment");
        }
    }
    IAccountFragment listener;

    public interface IAccountFragment{
        void editProfile();
        void logOut();
    }
}