package com.example.gradesdata;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.gradesdata.databinding.FragmentCourseBinding;

public class CourseFragment extends Fragment {

    FragmentCourseBinding binding;

    public CourseFragment() {
        // Required empty public constructor
    }

    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container,false);
        getActivity().setTitle("Add Courses");

        binding.buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){

                    RadioButton radioButton = binding.getRoot().findViewById(binding.radioGrp.getCheckedRadioButtonId());

                    listener.addNewCourse(new Course(binding.editTextNumber.getText().toString(), binding.editTextName.getText().toString(),
                            Integer.valueOf(binding.editTextHours.getText().toString()), radioButton.getText().toString()));
                }
            }
            
        });
        
        binding.buttonCancelCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goBack();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ICourseFragment){
            listener = (ICourseFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ICourseFragment");
        }
    }

    ICourseFragment listener;
    public interface ICourseFragment{

        void addNewCourse(Course course);

        void goBack();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextName.getText().toString().isEmpty()){
            binding.editTextName.setError("!");
            Toast.makeText(getContext(),"Enter Course Name!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextNumber.getText().toString().isEmpty()){
            binding.editTextNumber.setError("!");
            Toast.makeText(getContext(),"Enter Course Number!",Toast.LENGTH_LONG).show();
            good = false;
        }

        if(binding.editTextHours.getText().toString().isEmpty()){
            binding.editTextHours.setError("!");
            Toast.makeText(getContext(),"Enter Course Hours!",Toast.LENGTH_LONG).show();
            good = false;
        }
        return good;
    }
}