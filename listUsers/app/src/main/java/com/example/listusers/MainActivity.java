package com.example.listusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.listusers.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements UsersFragment.IUsersFragment,
    FilterByStateFragment.IFilterByStateFragment, SortFragment.ISortFragment {

    ActivityMainBinding binding;
    String states = "All States";
    String sortBy = "Name";
    boolean ascend = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new UsersFragment(), "user")
                .commit();
    }

    public ArrayList<DataServices.User> getUsers(){
        ArrayList<DataServices.User> mainUsers = DataServices.getAllUsers();

        Collections.sort(mainUsers, new Comparator<DataServices.User>() {
            @Override
            public int compare(DataServices.User user, DataServices.User t1) {
                int orderType;
                if (ascend){
                    orderType = 1;
                }else{
                    orderType = -1;
                }

                if (sortBy.equals("Name")){
                    return orderType * user.name.compareTo(t1.name);
                }else if (sortBy.equals("Age")){
                    return orderType * (user.age - t1.age);
                }else{
                    return orderType * user.state.compareTo(t1.state);
                }

            }
        });

        if (states.equals("All States")){
            return mainUsers;
        }else{
            ArrayList<DataServices.User> userArray = new ArrayList<>();
            for (DataServices.User user: mainUsers) {
                if (user.state.equals(states)){
                    userArray.add(user);
                }
            }
            return userArray;
        }


    }


    @Override
    public void filter() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new FilterByStateFragment(), "filter")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new SortFragment(), "sort")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<DataServices.User> getUserArray() {
        return getUsers();
    }

    @Override
    public ArrayList<String> getStatesArray() {
        ArrayList<DataServices.User> users = DataServices.getAllUsers();
        ArrayList<String> states = new ArrayList<>();

        for (DataServices.User user: users) {
            if(!states.contains(user.state)){
                states.add(user.state);
            }
        }
        Collections.sort(states);
        states.add(0, "All States");
        return states;
    }

    @Override
    public void selectState(String state) {
        states = state;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sortUsers(String sortBy, boolean ascend) {
        this.sortBy = sortBy;
        this.ascend = ascend;
        getSupportFragmentManager().popBackStack();
    }
}