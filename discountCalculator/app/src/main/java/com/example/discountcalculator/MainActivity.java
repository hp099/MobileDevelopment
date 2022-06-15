package com.example.discountcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
/*
    Assignment 2

    Group11_InClass02.zip

    Harsh Patel
    Kylee Mucher
 */
public class MainActivity extends AppCompatActivity {

    double discount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        TextView priceOut = findViewById(R.id.textViewPriceOut); //out label for final discounted price
        EditText userPrice = findViewById(R.id.editTextPrice); //initial price entered


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton5){
                    discount = 0.05;
                }else if (i == R.id.radioButton10){
                    discount = 0.10;
                }else if (i == R.id.radioButton15){
                    discount = 0.15;
                }else if (i == R.id.radioButton20){
                    discount = 0.20;
                }else if (i == R.id.radioButton50){
                    discount = 0.50;
                }
            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userPrice.getText().toString())){

                    Toast.makeText(getApplicationContext(),"Ticket Price Not Entered!",Toast.LENGTH_LONG).show();

                }else{
                    double calculateDiscount = Double.parseDouble(String.valueOf(userPrice.getText())) * (1- discount);

                    priceOut.setText(String.valueOf(String.format("$%.2f", calculateDiscount)));
                }


            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceOut.setText("");
                userPrice.setText("");
                radioGroup.check(R.id.radioButton5);
            }
        });
    }
}