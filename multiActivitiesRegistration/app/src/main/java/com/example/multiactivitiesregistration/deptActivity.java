package com.example.multiactivitiesregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/*
In Class Assignment 03
Patel_InClass03.zip
Harsh Patel
 */

public class deptActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);
        setTitle("Department");

        RadioGroup deptRadioGrp = findViewById(R.id.radioGrpDept);

        findViewById(R.id.buttonSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton dept = findViewById(deptRadioGrp.getCheckedRadioButtonId());
                String deptName = dept.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("ENTERED_DATA", deptName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}