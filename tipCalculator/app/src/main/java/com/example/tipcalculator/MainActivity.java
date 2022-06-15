package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
/*
Homework 1
Group11_HW1.zip
Harsh Patel
 */
public class MainActivity extends AppCompatActivity {

    int tipVal = 10;
    int split = 1;
    double tip;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RadioGroup tipRadioGrp = findViewById(R.id.radioGrpTip);// Tip Radio Group
        RadioGroup splitRadioGrp = findViewById(R.id.radioGrpSplit);// Split Radio Group
        EditText userInput = findViewById(R.id.editTextBill);// user entered price
        SeekBar customTip = findViewById(R.id.seekBarCustomTip);// custom tip

        TextView tipOut = findViewById(R.id.textViewTipOut);// tip output
        TextView totalOut = findViewById(R.id.textViewTotalOut);// total output
        TextView splitOut = findViewById(R.id.textViewPerPersonOut);// split out
        TextView seekBarVal = findViewById(R.id.textViewSeekBarVal);// updating val of seekbar progress

        // update seekbar choice
        customTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarVal.setText(String.format("%d%%", customTip.getProgress()));

                if ((tipRadioGrp.getCheckedRadioButtonId() == R.id.radioBtnCustom) && (userInput.getText().length() > 0)) {
                    tip = Double.parseDouble(String.valueOf(userInput.getText())) * (customTip.getProgress() / 100.0);
                    tipOut.setText(String.format("$%.2f", tip));
                    total = Double.parseDouble(String.valueOf(userInput.getText())) * ((customTip.getProgress() / 100.0) + 1);
                    totalOut.setText(String.format("$%.2f", total));
                    splitOut.setText(String.format("$%.2f", total / split));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // collect user input
        tipRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtn10:
                        tipVal = 10;

                        break;

                    case R.id.radioBtn15:
                        tipVal = 15;
                        break;

                    case R.id.radioBtn18:
                        tipVal = 18;
                        break;

                    case R.id.radioBtnCustom:
                        tipVal = customTip.getProgress();
                        ;
                        break;

                }
                if(userInput.getText().length() > 0){
                    tip = Double.parseDouble(String.valueOf(userInput.getText())) * (tipVal / 100.0);
                    tipOut.setText(String.format("$%.2f", tip));
                    total = Double.parseDouble(String.valueOf(userInput.getText())) * ((tipVal / 100.0) + 1);
                    totalOut.setText(String.format("$%.2f", total));
                    splitOut.setText(String.format("$%.2f", total / split));
                }

            }
        });

        // collect split
        splitRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnSplit1:
                        split = 1;
                        break;

                    case R.id.radioBtnSplit2:
                        split = 2;
                        break;

                    case R.id.radioBtnSplit3:
                        split = 3;
                        break;

                    case R.id.radioBtnSplit4:
                        split = 4;
                        break;
                }
                splitOut.setText(String.format("$%.2f", total / split));
            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText("");
                splitOut.setText("$0.00");
                tipOut.setText("$0.00");
                totalOut.setText("$0.00");
                customTip.setProgress(40);
                tipRadioGrp.check(R.id.radioBtn10);
                splitRadioGrp.check(R.id.radioBtnSplit1);
            }
        });

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    tip = Double.parseDouble(String.valueOf(userInput.getText())) * (tipVal / 100.0);
                    tipOut.setText(String.format("$%.2f", tip));
                    total = Double.parseDouble(String.valueOf(userInput.getText())) * ((tipVal / 100.0) + 1);
                    totalOut.setText(String.format("$%.2f", total));
                    splitOut.setText(String.format("$%.2f", total / split));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

}