package edu.uncc.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
In Class 08
Group11_InClass08.zip
Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener,
    CurrentWeatherFragment.ICurrentWeatherFragment{

    OkHttpClient client = new OkHttpClient();
    DataService.City currentCity;
    Weather currentWeather = new Weather();
    final String TAG = "demo";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if(message.getData().containsKey("weather")){



                    Weather weather = (Weather) message.getData().getSerializable("weather");
                    currentWeather = weather;

                    CurrentWeatherFragment fragment = (CurrentWeatherFragment) getSupportFragmentManager().findFragmentByTag("fragment");

                    if (fragment != null){
                        fragment.updateValues(weather);
                    }
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();
    }

    @Override
    public void gotoCurrentWeather(DataService.City city) {
        currentCity = city;
        ExecutorService taskPool = Executors.newFixedThreadPool(2);
        taskPool.execute(new DoWork());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CurrentWeatherFragment.newInstance(city), "fragment")
                .addToBackStack(null)
                .commit();
    }

    public class DoWork implements Runnable{

        public DoWork() {
        }

        @Override
        public void run() {

            HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                    .addQueryParameter("q", currentCity.toString())
                    .addQueryParameter("appid", "b088a7b6b3e7deb009516534593f938b")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d(TAG, "onResponse success");
                        Gson gson = new Gson();
                        Weather weather = gson.fromJson(response.body().charStream(), Weather.class);

                        Log.d(TAG, "onResponse" + weather);
                        sendMsg(weather);
                    }
                }
            });
        }

        private void sendMsg(Weather weather){
            Bundle bundle = new Bundle();
            bundle.putSerializable("weather", weather);

            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
}