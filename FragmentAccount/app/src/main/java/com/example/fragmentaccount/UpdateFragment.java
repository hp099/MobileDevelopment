package com.example.fragmentaccount;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentaccount.databinding.FragmentUpdateBinding;

/*
In Class Assignment 4
Group11_InClass04.zip
Harsh Patel
 */

public class UpdateFragment extends Fragment {
    private static final String USER_EMAIL = "userEmail";
    private String userEmail;

    FragmentUpdateBinding binding;

    public UpdateFragment() {
        // Required empty public constructor
    }


    public static UpdateFragment newInstance(String email) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(USER_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.userEmail = getArguments().getString(USER_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateBinding.inflate(inflater,container,false);

        binding.textViewEmail.setText(userEmail);

        binding.buttonUpdateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.submitUpdate(binding.editTextUpdateName.getText().toString(),binding.editTextUpdatePassword.getText().toString());
            }
        });

        binding.buttonCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelUpdate();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof UpdateFragment.IUpdateFragment){
            listener = (UpdateFragment.IUpdateFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IUpdateFragment");
        }

    }

    IUpdateFragment listener;

    public interface IUpdateFragment{
        void cancelUpdate();
        void submitUpdate(String name, String password);
    }
}