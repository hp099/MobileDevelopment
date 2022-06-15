package com.example.postapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.postapp.databinding.FragmentNewAccountBinding;

public class NewAccountFragment extends Fragment {

    FragmentNewAccountBinding binding;

    public NewAccountFragment() {
        // Required empty public constructor
    }

    public static NewAccountFragment newInstance(String param1, String param2) {
        NewAccountFragment fragment = new NewAccountFragment();
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
        binding = FragmentNewAccountBinding.inflate(inflater, container, false);
        getActivity().setTitle("Signup");

        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    listener.submit(binding.editTextName.getText().toString(), binding.editTextCreateEmail.getText().toString(), binding.editTextCreatePassword.getText().toString());
                }
            }
        });

        binding.buttonCancelCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelCreateAccount();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof INewAccountFragment){
            listener = (INewAccountFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement INewAccountFragment");
        }
    }

    INewAccountFragment listener;
    public interface INewAccountFragment{
        void submit(String name, String email, String password);
        void cancelCreateAccount();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextCreateEmail.getText().toString().isEmpty()){
            binding.editTextCreateEmail.setError("!");
            Toast.makeText(getContext(),"Enter Email!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextCreatePassword.getText().toString().isEmpty()){
            binding.editTextCreatePassword.setError("!");
            Toast.makeText(getContext(),"Enter Password!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextName.getText().toString().isEmpty()){
            binding.editTextName.setError("!");
            Toast.makeText(getContext(),"Enter Name!",Toast.LENGTH_LONG).show();
            good = false;
        }

        return good;
    }
}