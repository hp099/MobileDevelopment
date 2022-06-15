package com.example.forumapp;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    String title;
    String creatorName;
    String description;
    String dateTime;
    String userId;
    String id;
    List<String> likes = new ArrayList<>();
    List<String> comments = new ArrayList<>();

    public Forum(String title, String creatorName, String description, String dateTime, String userId, String id, List<String> likes) {
        this.title = title;
        this.creatorName = creatorName;
        this.description = description;
        this.dateTime = dateTime;
        this.userId = userId;
        this.id = id;
        this.likes = likes;
    }
    public Forum(){

    }

    public Forum(String title, String creatorName, String description, String dateTime, String userId, String id, List<String> likes, List<String> comments) {
        this.title = title;
        this.creatorName = creatorName;
        this.description = description;
        this.dateTime = dateTime;
        this.userId = userId;
        this.id = id;
        this.likes = likes;
        this.comments = comments;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public int getNoOfLikes(){
        if (likes == null){
            return 0;
        }
        return likes.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
