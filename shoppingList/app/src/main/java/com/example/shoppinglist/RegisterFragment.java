package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppinglist.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        getActivity().setTitle("Signup");

        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    db = FirebaseFirestore.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(binding.editTextCreateEmail.getText().toString(), binding.editTextCreatePassword.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(),"Signup Successful",Toast.LENGTH_LONG).show();

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(binding.editTextName.getText().toString()).build())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getContext(),"Name Update Successful",Toast.LENGTH_LONG).show();

                                                            // adding user info to db users
                                                            db.collection("users").add(new user(mAuth.getCurrentUser().getDisplayName(), mAuth.getUid()))
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                            listener.goToLists();
                                                                        }
                                                                    });
                                                        }else{
                                                            Toast.makeText(getContext(),"Name Update Not Successful",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
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
        if (context instanceof IRegisterFragment){
            listener = (IRegisterFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IRegisterFragment");
        }
    }

    IRegisterFragment listener;
    public interface IRegisterFragment{
        void goToLists();
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