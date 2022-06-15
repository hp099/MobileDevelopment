package com.example.forumapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.forumapp.databinding.FragmentCreateBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CreateFragment extends Fragment {
    FragmentCreateBinding binding;
    private FirebaseAuth mAuth;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        getActivity().setTitle("Create Post");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    Calendar calendar = Calendar.getInstance();
                    String currentDateTime = DateFormat.getDateTimeInstance().format(calendar.getTime());


                    mAuth = FirebaseAuth.getInstance();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    HashMap<String, Object> forum = new HashMap<>();
                    forum.put("title", binding.editTextTitle.getText().toString());
                    forum.put("creatorName", mAuth.getCurrentUser().getDisplayName());
                    forum.put("description", binding.editTextDescription.getText().toString());
                    forum.put("dateTime", currentDateTime);
                    forum.put("userId", mAuth.getCurrentUser().getUid());
                    forum.put("likes", Arrays.asList());
                    forum.put("comments", Arrays.asList());

                        db.collection("forums")
                                .add(forum)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getContext(), "Forum Created Successfully", Toast.LENGTH_LONG).show();
                                        forum.put("id", documentReference.getId());
                                        db.collection("forums").document(documentReference.getId())
                                                .update(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Forum ID added", Toast.LENGTH_LONG).show();
                                                listener.goToForums();
                                            }
                                        });
                                    }
                                });
                }
            }
        });

        binding.buttonCancelCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goToForums();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ICreateFragment){
            listener = (ICreateFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ICreateFragment");
        }
    }

    ICreateFragment listener;
    public interface ICreateFragment{
        void goToForums();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextTitle.getText().toString().isEmpty()){
            binding.editTextTitle.setError("!");
            Toast.makeText(getContext(),"Enter Title!",Toast.LENGTH_LONG).show();
            good = false;
        }
        if(binding.editTextDescription.getText().toString().isEmpty()){
            binding.editTextDescription.setError("!");
            Toast.makeText(getContext(),"Enter Description!",Toast.LENGTH_LONG).show();
            good = false;
        }
        return good;
    }
}