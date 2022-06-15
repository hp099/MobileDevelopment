package com.example.expensetracking;

import java.util.Calendar;
import java.util.Date;

public class Expense {
    String name;
    double amount;
    Calendar date;
    String category;

    public Expense(String name, double amount, Calendar date, String category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int compareTo(Expense e) {
        return date.compareTo(e.getDate());
    }
}
