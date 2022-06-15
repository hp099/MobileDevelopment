package com.example.gradesdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String number;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public int hours;

    @ColumnInfo
    public String grade;

    public Course(long id, String number, String name, int hours, String grade) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.hours = hours;
        this.grade = grade;
    }

    public Course(String number, String name, int hours, String grade) {
        this.number = number;
        this.name = name;
        this.hours = hours;
        this.grade = grade;
    }


    public Course(){}

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", grade=" + grade +
                '}';
    }
}
