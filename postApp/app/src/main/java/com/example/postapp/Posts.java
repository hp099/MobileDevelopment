package com.example.postapp;

public class Posts {
    String created_by_name;
    String post_id;
    String created_by_uid;
    String post_text;
    String created_at;

    public Posts(String created_by_name, String post_id, String created_by_uid, String post_text, String created_at) {
        this.created_by_name = created_by_name;
        this.post_id = post_id;
        this.created_by_uid = created_by_uid;
        this.post_text = post_text;
        this.created_at = created_at;
    }

    public String getCreated_by_name() {
        return created_by_name;
    }

    public void setCreated_by_name(String created_by_name) {
        this.created_by_name = created_by_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCreated_by_uid() {
        return created_by_uid;
    }

    public void setCreated_by_uid(String created_by_uid) {
        this.created_by_uid = created_by_uid;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
