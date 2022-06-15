package com.example.gradesdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * from courses")
    List<Course> getAll();

    @Query("SELECT * from courses WHERE id = :id limit 1")
    Course findById(long id);

    @Update
    void update(Course course);

    @Insert
    void insertAll(Course... courses);

    @Delete
    void delete(Course course);
}
