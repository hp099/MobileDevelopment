package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppinglist.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        getActivity().setTitle("Login");

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                        listener.goToLists();
                                    }else{
                                        Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        binding.buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.gotoCreateNewAccount();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ILoginFragment){
            listener = (ILoginFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ILoginFragment");
        }
    }

    ILoginFragment listener;

    public interface ILoginFragment{
        void goToLists();
        void gotoCreateNewAccount();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextEmail.getText().toString().isEmpty()){
            binding.editTextEmail.setError("!");
            Toast.makeText(getContext(),"Enter Email!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextPassword.getText().toString().isEmpty()){
            binding.editTextPassword.setError("!");
            Toast.makeText(getContext(),"Enter Password!",Toast.LENGTH_LONG).show();
            good = false;
        }

        return good;
    }
}