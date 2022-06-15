package com.example.expensetracking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.expensetracking.databinding.ActivityMainBinding;

import java.util.ArrayList;
/*
Harsh Patel
Midterm
 */
public class MainActivity extends AppCompatActivity implements ExpenseFragment.IExpenseFragment,
    AddExpenseFragment.IAddExpenseFragment, PickCategoryFragment.IPickCategoryFragment{

    ActivityMainBinding binding;
    ArrayList<Expense> expenses = new ArrayList<Expense>();
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new ExpenseFragment(), "expenses")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addExp() {
        category = "None";
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new AddExpenseFragment(), "add")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void expSummary() {
        // check SummaryFragment for partial implementation, couldn't finish because of time constraint
    }

    @Override
    public ArrayList<Expense> getExpenses() {

        return expenses;
    }

    @Override
    public void deleteExp(Expense exp) {
        expenses.remove(exp);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new ExpenseFragment(), "expenses")
                .commit();
    }

    @Override
    public ArrayList<String> getCatArray() {
        return DataServices.getCategories();
    }

    @Override
    public void selectItem(String s) {
        category = s;
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public String getString() {
        return category;
    }

    @Override
    public void pickCat() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new PickCategoryFragment(), "cat")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addExpense(Expense exp) {
        expenses.add(exp);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelAdd() {
        getSupportFragmentManager().popBackStack();
    }
}