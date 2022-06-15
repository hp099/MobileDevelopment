package com.example.postapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.postapp.databinding.FragmentCreatePostBinding;

public class CreatePostFragment extends Fragment {
    FragmentCreatePostBinding binding;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static CreatePostFragment newInstance(String param1, String param2) {
        CreatePostFragment fragment = new CreatePostFragment();
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
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        getActivity().setTitle("Create Post");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    listener.submitPost(binding.editTextPostCreateText.getText().toString());
                }
            }
        });

        binding.buttonCancelCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelCreatePost();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ICreatePostFragment){
            listener = (ICreatePostFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ICreatePostFragment");
        }
    }

    ICreatePostFragment listener;
    public interface ICreatePostFragment{
        void submitPost(String postText);
        void cancelCreatePost();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextPostCreateText.getText().toString().isEmpty()){
            binding.editTextPostCreateText.setError("!");
            Toast.makeText(getContext(),"Enter Text!",Toast.LENGTH_LONG).show();
            good = false;
        }

        return good;
    }
}