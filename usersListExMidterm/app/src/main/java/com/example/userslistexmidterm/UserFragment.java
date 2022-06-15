package com.example.userslistexmidterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.userslistexmidterm.databinding.FragmentUserBinding;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        binding = FragmentUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IUserFragment){
            listener = (IUserFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IUserFragment");
        }
    }

    IUserFragment listener;
    public interface IUserFragment{
    }
}