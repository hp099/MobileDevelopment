package com.example.shoppinglist;

public class item {
    String name;
    Boolean purchased;

    public item(String name, Boolean purchased) {
        this.name = name;
        this.purchased = purchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}
