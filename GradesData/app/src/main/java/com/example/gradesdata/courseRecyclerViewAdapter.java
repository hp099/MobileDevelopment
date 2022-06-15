package com.example.gradesdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class courseRecyclerViewAdapter extends RecyclerView.Adapter<courseRecyclerViewAdapter.coursesViewHolder>{
    List<Course> courses;
    IcourseRecycler listener;

    public courseRecyclerViewAdapter(List<Course> data, IcourseRecycler listener){
        this.courses = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public coursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        coursesViewHolder courseViewHolder = new coursesViewHolder(view, listener);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull coursesViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.number.setText(course.number);
        holder.name.setText(course.name);
        holder.hours.setText(String.valueOf(course.hours));
        holder.grade.setText(course.grade);
        
        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.delete(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class coursesViewHolder extends RecyclerView.ViewHolder{
        IcourseRecycler listener;
        TextView number;
        TextView name;
        TextView hours;
        TextView grade;
        ImageButton trashButton;

        public coursesViewHolder(@NonNull View itemView, IcourseRecycler listener) {
            super(itemView);

            this.listener = listener;
            this.number = itemView.findViewById(R.id.textViewCourseNum);
            this.name = itemView.findViewById(R.id.textViewCourseName);
            this.hours = itemView.findViewById(R.id.textViewCreditHours);
            this.grade = itemView.findViewById(R.id.textViewCourseGrade);
            this.trashButton = itemView.findViewById(R.id.imageButtonTrash);
        }
    }

    interface IcourseRecycler{
        void delete(Course course);

    }
}
