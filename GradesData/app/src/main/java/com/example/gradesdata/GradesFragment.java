package com.example.gradesdata;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradesdata.databinding.FragmentGradesBinding;

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment implements courseRecyclerViewAdapter.IcourseRecycler{
    FragmentGradesBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    courseRecyclerViewAdapter adapter;
    List<Course> courseLists = new ArrayList<>();
    int totalHrs;
    double totalPnts;


    public GradesFragment() {
        // Required empty public constructor
    }

    public static GradesFragment newInstance(String param1, String param2) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        getActivity().setTitle("Grades");

        courseLists = listener.getAllCourses();

        totalHrs = 0;
        totalPnts = 0;

        for (Course course:courseLists) {
            totalHrs += course.hours;

            double grade = 0;
            switch (course.grade){
                case "A":
                    grade = 4.0;
                    break;
                case "B":
                    grade = 3.0;
                    break;
                case "C":
                    grade = 2.0;
                    break;
                case "D":
                    grade = 1.0;
                    break;
                case "F":
                    grade = 0.0;
                    break;
            }

            totalPnts += (course.hours * grade);
        }

        binding.textViewGPA.setText(String.format("GPA: %.2f", (totalPnts/totalHrs)));
        binding.textViewHours.setText("Hours: " + totalHrs);

        recyclerView = binding.recyclerViewPages;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new courseRecyclerViewAdapter(courseLists, this);
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IGradesFragment){
            listener = (IGradesFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IGradesFragment");
        }
    }

    IGradesFragment listener;

    @Override
    public void delete(Course course) {
        listener.delete(course);
    }

    public void updateValues(List<Course> all) {
        courseLists = all;
    }

    public interface IGradesFragment{

        List<Course> getAllCourses();

        void delete(Course course);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}