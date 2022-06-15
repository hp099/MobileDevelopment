package com.example.ituneapps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.ituneapps.databinding.FragmentAppListBinding;

import java.util.ArrayList;

/*
Assignment InClass05
Group11_InClass05.zip
Harsh Patel
 */

public class AppListFragment extends Fragment {
    FragmentAppListBinding binding;
    ArrayList<DataServices.App> appsArrayList;
    AppAdapter adapter;

    public AppListFragment() {
        // Required empty public constructor
    }

    public static AppListFragment newInstance() {
        AppListFragment fragment = new AppListFragment();
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
        binding = FragmentAppListBinding.inflate(inflater, container, false);
        getActivity().setTitle(listener.getGenre());

        appsArrayList = listener.getApps();
        adapter = new AppAdapter(getContext(), R.layout.app_row_item, appsArrayList);
        binding.listViewApps.setAdapter(adapter);

        binding.listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataServices.App app = appsArrayList.get(i);
                listener.selectApp(app);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if (context instanceof IAppListFragment){
            listener = (IAppListFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IAppListFragment");
        }
    }

    IAppListFragment listener;
    public interface IAppListFragment{
        ArrayList<DataServices.App> getApps();
        void selectApp(DataServices.App app);
        String getGenre();
    }
}