package edu.uncc.sis.itcs4180.fragmentsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactsFragment.IContactsFragment {

    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the fragment
    }

    @Override
    public void deleteContact(String contactId) {

    }

    @Override
    public void viewContactDetails(String contactId) {
        //Replace current fragment with the ContactDetailsFragment
    }

    @Override
    public void goToCreate() {
        // Example of changing activity
        startActivity(new Intent(this, CreateActivity.class));
    }
}