package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = findViewById(R.id.textViewMid);
        //textView.setText("Hello World");

        //findViewById(R.id.button2).setOnClickListener(this);

        Log.d(TAG, "onCreate: "); // displaying message in console(Logcat)

        // quick messages displaying in the app (can use .length_long also)
        Toast.makeText(this, "Testing Toasts !", Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");

    }*/
}