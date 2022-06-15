package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
/*
Homework 2
HW02.zip
Harsh Patel
 */

public class Task implements Serializable{
    String name;
    Calendar dateDue;
    String priority;

    public Task(String name, Calendar dateDue, String priority) {
        this.name = name;
        this.dateDue = dateDue;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Calendar getDateDue() {
        return dateDue;
    }

    public String getPriority() {
        return priority;
    }

    public int compareTo(Task o) {
        return dateDue.compareTo(o.getDateDue());
    }
}
