package com.example.fragmentaccount.ui.login;

import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentaccount.DataServices;
import com.example.fragmentaccount.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataServices.AccountRequestTask task = DataServices.login(binding.email.getText().toString(), binding.password.getText().toString());

                if(task.isSuccessful()){
                    DataServices.Account account = task.getAccount();
                }else{
                    String error = task.getErrorMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

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

    public interface ILoginFragment {
        void login(String userName, String Password);
        void createNewAccount();
    }

}