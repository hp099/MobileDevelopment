package com.example.fragmentaccount;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentaccount.databinding.FragmentLoginBinding;

/*
In Class Assignment 4
Group11_InClass04.zip
Harsh Patel
 */

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.login(binding.email.getText().toString(), binding.password.getText().toString());

            }
        });

        binding.newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.createNewAccount();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof ILoginFragment){
            listener = (ILoginFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ILoginFragment");
        }
    }

    ILoginFragment listener;

    public interface ILoginFragment{
        void login(String email, String password);
        void createNewAccount();
    }

}