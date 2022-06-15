package com.example.threadandhandlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.threadandhandlers.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
In Class 06
Group11_InClass06.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int complexity = 8;
    Handler handler;
    ArrayAdapter<Double> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Main Activity");

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if(message.getData().containsKey("iter")){
                    int iter = message.getData().getInt("iter");
                    binding.textViewProgress.setText(String.format("%d/%d", iter, complexity));
                    binding.progressBar.setMax(complexity);
                    binding.progressBar.setProgress(iter, true);
                    if (iter == complexity){
                        binding.buttonGenerate.setEnabled(true);
                        binding.seekBar.setEnabled(true);
                    }
                }
                if(message.getData().containsKey("avg")){
                    double avg = message.getData().getDouble("avg");
                    binding.textViewAvg.setText(String.format("Average: %f", avg));
                }
                if(message.getData().containsKey("array")){
                    ArrayList<Double> array = (ArrayList<Double>) message.getData().getSerializable("array");
                    adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, array);
                    binding.ListView.setAdapter(adapter);
                }

                return true;
            }
        });

        ExecutorService taskPool = Executors.newFixedThreadPool(2);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                complexity = i;
                binding.textViewTimes.setText(complexity + " Times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.buttonGenerate.setEnabled(false);
                binding.seekBar.setEnabled(false);
                binding.progressBar.setVisibility(View.VISIBLE);
                taskPool.execute(new DoWork(complexity));
            }
        });

    }

    public class DoWork implements Runnable{
        int complexVal;

        public DoWork(int complexVal) {
            this.complexVal = complexVal;
        }

        @Override
        public void run() {
            double sum = 0;
            ArrayList<Double> array = new ArrayList<>();

            sendMsg(new ArrayList<Double>(), 0, 0);

            for (int i = 0; i < complexVal; i++) {
                double temp = HeavyWork.getNumber();
                array.add(temp);
                sum += temp;

                sendMsg(array, (i+1), (sum / (i+1)));
            }

        }

        private void sendMsg(ArrayList<Double> array, int iter, double avg){
            Bundle bundle = new Bundle();
            bundle.putSerializable("array", array);
            bundle.putInt("iter", iter);
            bundle.putDouble("avg", avg);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
}