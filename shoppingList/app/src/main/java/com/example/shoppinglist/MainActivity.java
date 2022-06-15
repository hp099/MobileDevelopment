package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/*
    Harsh Patel
    HW 07
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment,
    RegisterFragment.IRegisterFragment, ShoppingFragment.IShoppingFragment, CreateFragment.ICreateFragment,
    itemsFragment.IItemsFragment{

    FirebaseAuth mAuth;
    String shpLstId = "";

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
                    .add(R.id.rootView, new ShoppingFragment(), "shopping")
                    .commit();
        }
    }

    @Override
    public void openCreateList() {
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
    public void gotoCreateNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment(), "register")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToLists() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ShoppingFragment(), "shopping")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openItems(String id) {
        shpLstId = id;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new itemsFragment(), "items")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelCreateAccount() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public String getForumID() {
        return shpLstId;
    }

    @Override
    public void updateItems() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new itemsFragment(), "items")
                .addToBackStack(null)
                .commit();
    }
}