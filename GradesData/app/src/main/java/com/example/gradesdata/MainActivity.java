package com.example.gradesdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
    InClass 10
    Group11_InClass10.zip
    Harsh Patel
 */
public class MainActivity extends AppCompatActivity implements GradesFragment.IGradesFragment,
    CourseFragment.ICourseFragment{

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this, AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        /*db.courseDao().insertAll(new Course("math", "100"),
                new Course("science", "80"));

        Log.d("demo", "onCreate: " + db.courseDao().getAll());

        Course course = db.courseDao().findById(1);

        course.subject = "90";
        db.courseDao().update(course);*/

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new GradesFragment(), "grades")
                .commit();
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootView, new CourseFragment(), "course")
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return db.courseDao().getAll();
    }

    @Override
    public void delete(Course course) {
        db.courseDao().delete(course);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new GradesFragment(), "grades")
                .commit();
    }

    @Override
    public void addNewCourse(Course course) {
        db.courseDao().insertAll(course);

        getSupportFragmentManager().popBackStack();

        updateGrades();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }

    private void updateGrades() {

        GradesFragment frag = (GradesFragment) getSupportFragmentManager().findFragmentByTag("grades");

        if (frag != null){
            frag.updateValues(db.courseDao().getAll());
        }
    }
}