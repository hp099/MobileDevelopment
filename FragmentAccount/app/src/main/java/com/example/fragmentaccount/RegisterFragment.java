package com.example.fragmentaccount;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentaccount.databinding.FragmentRegisterBinding;

/*
In Class Assignment 4
Group11_InClass04.zip
Harsh Patel
 */

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        /*Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.newAccount(binding.newAccountName.getText().toString(), binding.newAccountEmail.getText().toString(), binding.newAccountPassword.getText().toString());
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelReg();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof RegisterFragment.IRegisterFragment){
            listener = (RegisterFragment.IRegisterFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IRegisterFragment");
        }

    }

    IRegisterFragment listener;

    public interface IRegisterFragment{
        void newAccount(String name, String email, String password);
        void cancelReg();
    }
}