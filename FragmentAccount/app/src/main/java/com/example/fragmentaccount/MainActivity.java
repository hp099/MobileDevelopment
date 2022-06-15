package com.example.fragmentaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fragmentaccount.databinding.ActivityMainBinding;
import com.example.fragmentaccount.ui.login.LoginFragment;
/*
In Class Assignment 4
Group11_InClass04.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment,
        RegisterFragment.IRegisterFragment, AccountFragment.IAccountFragment, UpdateFragment.IUpdateFragment {

    ActivityMainBinding binding;
    DataServices.Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Login");



    }

    @Override
    public void login(String email, String password) {
        DataServices.AccountRequestTask task = DataServices.login(email, password);
        if (task.isSuccessful()){
            account = task.getAccount();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, AccountFragment.newInstance(account.getName()), "account")
                    .commit();
        }else{
            String error = task.getErrorMessage();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new RegisterFragment(), "register")
                .commit();
    }

    @Override
    public void newAccount(String name, String email, String password) {
        DataServices.AccountRequestTask task = DataServices.register(name, email, password);

        if (task.isSuccessful()){
            account = task.getAccount();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, AccountFragment.newInstance(account.getName()), "account")
                    .commit();

        }else{
            String error = task.getErrorMessage();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancelReg() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void editProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, UpdateFragment.newInstance(account.getEmail()), "Update")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logOut() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void cancelUpdate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, AccountFragment.newInstance(account.getName()), "account")
                .commit();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitUpdate(String name, String password) {
        DataServices.AccountRequestTask task =  DataServices.update(account, name, password);

        if (task.isSuccessful()){
            account = task.getAccount();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, AccountFragment.newInstance(account.getName()), "account")
                    .commit();
        }else{
            String error = task.getErrorMessage();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }
}