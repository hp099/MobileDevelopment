package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    Weather currentWeather;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);

        /*currentWeather = listener.getWeather();
        if (currentWeather != null){// temp
            binding.textViewCity.setText(currentWeather.name);
            binding.textViewTemp.setText(String.valueOf(currentWeather.main.temp));
            binding.textViewMaxTemp.setText(String.valueOf(currentWeather.main.temp_max));
            binding.textViewMinTemp.setText(String.valueOf(currentWeather.main.temp_min));
            binding.textViewDescript.setText(String.valueOf(currentWeather.weather.get(0).description));
            binding.textViewHumidity.setText(String.valueOf(currentWeather.main.humidity));
            binding.textViewWindSpeed.setText(String.valueOf(currentWeather.wind.speed));
            binding.textViewWindDegree.setText(String.valueOf(currentWeather.wind.deg));
            binding.textViewCloud.setText(String.valueOf(currentWeather.clouds.all));
        }*/

        return binding.getRoot();
    }

    void updateValues(Weather weather){
        currentWeather = weather;
        if (currentWeather != null){// temp
            binding.textViewCity.setText(currentWeather.name);
            binding.textViewTemp.setText(String.valueOf(currentWeather.main.temp));
            binding.textViewMaxTemp.setText(String.valueOf(currentWeather.main.temp_max));
            binding.textViewMinTemp.setText(String.valueOf(currentWeather.main.temp_min));
            binding.textViewDescript.setText(String.valueOf(currentWeather.weather.get(0).description));
            binding.textViewHumidity.setText(String.valueOf(currentWeather.main.humidity));
            binding.textViewWindSpeed.setText(String.valueOf(currentWeather.wind.speed));
            binding.textViewWindDegree.setText(String.valueOf(currentWeather.wind.deg));
            binding.textViewCloud.setText(String.valueOf(currentWeather.clouds.all));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ICurrentWeatherFragment){
            listener = (ICurrentWeatherFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ICurrentWeatherFragment");
        }
    }
    ICurrentWeatherFragment listener;
    public interface ICurrentWeatherFragment{

    }
}