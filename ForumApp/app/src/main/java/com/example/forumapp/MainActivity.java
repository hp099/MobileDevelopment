package com.example.forumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/*
    HW06
    Patel_HW06.zip
    Harsh Patel
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment,
    RegisterFragment.IRegisterFragment, ForumsFragment.IForumListFragment, CreateFragment.ICreateFragment,
    DetailsFragment.IDetailsFragment{

    FirebaseAuth mAuth;
    String forumDetailId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new LoginFragment(), "login")
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new ForumsFragment(), "forums")
                    .commit();
        }
    }

    @Override
    public void openCreateForum() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateFragment(), "create")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logOut() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void goToForums() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment(), "forums")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openForumDetail(String forumId) {
        forumDetailId = forumId;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new DetailsFragment(), "details")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelCreateAccount() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void gotoCreateNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment(), "register")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public String getForumID() {
        return forumDetailId;
    }

    @Override
    public void updateComments() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new DetailsFragment(), "details")
                .addToBackStack(null)
                .commit();
    }
}