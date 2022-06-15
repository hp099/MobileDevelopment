package com.example.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingList {
    String title;
    String creatorName;
    String userId;
    String id;
    HashMap<String, Boolean> items = new HashMap<>();
    ArrayList<String> guests = new ArrayList<>();

    public ShoppingList(String title, String creatorName, String userId, String id, HashMap<String, Boolean> items, ArrayList<String> guests) {
        this.title = title;
        this.creatorName = creatorName;
        this.userId = userId;
        this.id = id;
        this.items = items;
        this.guests = guests;
    }

    public ShoppingList(String title, String creatorName, String userId, ArrayList<String> guests) {
        this.title = title;
        this.creatorName = creatorName;
        this.userId = userId;
        this.guests = guests;
    }
    public ShoppingList(){

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

    public HashMap<String, Boolean> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Boolean> items) {
        this.items = items;
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<String> guests) {
        this.guests = guests;
    }
}
