package com.example.ituneapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ituneapps.databinding.ActivityMainBinding;

import java.util.ArrayList;

/*
Assignment InClass05
Group11_InClass05.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.IAppCategoriesFragment,
    AppListFragment.IAppListFragment, AppDetailsFragment.IAppDetailsFragment{

    ActivityMainBinding binding;
    String category;
    DataServices.App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new AppCategoriesFragment(), "appCat")
                .commit();

    }

    @Override
    public ArrayList<String> getCatArray() {
        return DataServices.getAppCategories();
    }

    @Override
    public void selectItem(String s) {
        category = s;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new AppListFragment(), "appList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<DataServices.App> getApps() {
        return DataServices.getAppsByCategory(category);
    }

    @Override
    public void selectApp(DataServices.App app) {
        this.app = app;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new AppDetailsFragment(), "appDetail")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public String getGenre() {
        return category;
    }

    @Override
    public DataServices.App getApp() {
        return app;
    }

    @Override
    public ArrayList<String> getGenreArray() {
        return app.genres;
    }
}