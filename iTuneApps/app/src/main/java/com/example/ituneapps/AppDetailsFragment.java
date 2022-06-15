package com.example.ituneapps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.ituneapps.databinding.FragmentAppDetailsBinding;

import java.util.ArrayList;

/*
Assignment InClass05
Group11_InClass05.zip
Harsh Patel
 */

public class AppDetailsFragment extends Fragment {

    FragmentAppDetailsBinding binding;
    ArrayAdapter<String> adapter;
    ArrayList<String> genreArray;
    DataServices.App app;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    public static AppDetailsFragment newInstance(String param1, String param2) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppDetailsBinding.inflate(inflater, container, false);
        getActivity().setTitle("App Details");

        app = listener.getApp();
        genreArray = listener.getGenreArray();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, genreArray);
        binding.listViewGenres.setAdapter(adapter);
        binding.textViewAppName.setText(app.name);
        binding.textViewArtistName.setText(app.artistName);
        binding.textViewDate.setText(app.releaseDate);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof IAppDetailsFragment){
            listener = (IAppDetailsFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IAppDetailsFragment");
        }
    }

    IAppDetailsFragment listener;
    public interface IAppDetailsFragment{
        DataServices.App getApp();
        ArrayList<String> getGenreArray();
    }
}