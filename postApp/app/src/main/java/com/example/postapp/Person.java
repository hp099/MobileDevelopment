package com.example.postapp;

import java.io.Serializable;

public class Person implements Serializable {
    String status;
    String token;
    String user_fullname;
    Integer user_id;

    public Person(String user_fullname, Integer user_id) {
        this.status = status;
        this.token = token;
        this.user_fullname = user_fullname;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "user_fullname='" + user_fullname + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
