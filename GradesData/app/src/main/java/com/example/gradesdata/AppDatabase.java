package com.example.gradesdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
