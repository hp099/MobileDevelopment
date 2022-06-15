package com.example.postapp;

import java.io.Serializable;
import java.util.ArrayList;

public class PostList implements Serializable {
    String status;
    ArrayList<Posts> posts;
    String page;
    int pageSize;
    int totalCount;

    public PostList(String status, ArrayList<Posts> posts, String page, int pageSize, int totalCount) {
        this.status = status;
        this.posts = posts;
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
