package com.example.multiactivitiesregistration;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
In Class Assignment 03
Patel_InClass03.zip
Harsh Patel
 */

public class registActivity extends AppCompatActivity {
    final static public int REQ_CODE = 100;
    final static public String USER_KEY = "USER";
    TextView departName;
    EditText name, email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setTitle("Registration");

        departName = findViewById(R.id.textViewDeptOut);
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        id = findViewById(R.id.editTextID);

        // getting department
        findViewById(R.id.buttonSelectDept).setOnClickListener((view -> {
            Intent intent = new Intent(registActivity.this, deptActivity.class);
            startActivityForResult(intent, REQ_CODE);
        }));

        // submitting user data
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckAllFields()){
                    Intent intent = new Intent(registActivity.this, profileActivity.class);
                    intent.putExtra(USER_KEY, new Profile(name.getText().toString(), email.getText().toString(), id.getText().toString(), departName.getText().toString()));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            if(data != null && data.hasExtra("ENTERED_DATA")){
                departName.setText(data.getStringExtra("ENTERED_DATA"));
            }
        }
    }

    private boolean CheckAllFields(){
        boolean good = true;
        if(name.getText().toString().isEmpty()){
            name.setError("!");
            Toast.makeText(getApplicationContext(),"Invalid Name Entry!",Toast.LENGTH_LONG).show();
            good = false;
        }
        if (email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("!");
            Toast.makeText(getApplicationContext(), "Invalid E-mail Entry!", Toast.LENGTH_SHORT).show();
            good =  false;
        }
        if (id.getText().toString().isEmpty()){
            id.setError("!");
            Toast.makeText(getApplicationContext(), "Invalid ID Entry!", Toast.LENGTH_SHORT).show();
            good = false;
        }
        if(departName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Department not selected!", Toast.LENGTH_SHORT).show();
            good = false;
        }
        return good;
    }
}