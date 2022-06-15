package com.example.multiactivitiesregistration;

import java.io.Serializable;
/*
In Class Assignment 03
Patel_InClass03.zip
Harsh Patel
 */
public class Profile implements Serializable {
    String name;
    String email;
    String id;
    String department;

    public Profile(String name, String email, String id, String department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
