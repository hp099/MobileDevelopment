package com.example.multiactivitiesregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
/*
In Class Assignment 03
Patel_InClass03.zip
Harsh Patel
 */
public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        TextView name = findViewById(R.id.textViewName);
        TextView email = findViewById(R.id.textViewEmail);
        TextView id = findViewById(R.id.textViewID);
        TextView dept = findViewById(R.id.textViewDept);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(registActivity.USER_KEY)){
            Profile user = (Profile) getIntent().getSerializableExtra(registActivity.USER_KEY);
            name.setText(user.getName());
            email.setText(user.getEmail());
            id.setText(user.getId());
            dept.setText(user.getDepartment());
        }
    }
}