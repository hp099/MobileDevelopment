package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
    In Class 11
    Group11_InClass11.zip
    Harsh Patel
 */

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    OkHttpClient client = new OkHttpClient();
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService taskPool = Executors.newFixedThreadPool(2);
        taskPool.execute(new getRoute());

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if(message.getData().containsKey("route")){
                    Route route = (Route) message.getData().getSerializable("route");

                    // Instantiates a new Polyline object and adds points to define a rectangle
                    PolylineOptions polylineOptions = new PolylineOptions();

                    if (route != null){
                        double northLat = -1000;// north most lat
                        double southLat = 1000;// south most lat
                        double eastLon = -1000;// east most lon
                        double westLon = 1000;// west most lon

                        for (Coordinates cor: route.path) {
                            double lat = cor.latitude;
                            double lon = cor.longitude;
                            polylineOptions.add(new LatLng(lat, lon));

                            if (lat > northLat){
                                northLat = lat;
                            }
                            if (lat < southLat){
                                southLat = lat;
                            }
                            if (lon > eastLon){
                                eastLon = lon;
                            }
                            if (lon < westLon){
                                westLon = lon;
                            }
                        }

                        LatLng start = new LatLng(route.path.get(0).latitude, route.path.get(0).longitude);
                        LatLng stop = new LatLng(route.path.get(route.path.size() - 1).latitude, route.path.get(route.path.size() - 1).longitude);
                        LatLng NE = new LatLng(northLat, eastLon);
                        LatLng SW = new LatLng(southLat, westLon);
                        LatLngBounds bounds = new LatLngBounds(SW, NE);

                        mMap.addMarker(new MarkerOptions()
                                .position(start)
                                .title("Start"));
                        mMap.addMarker(new MarkerOptions()
                                .position(stop)
                                .title("Stop"));

                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,100));

                    }

                    // Get back the mutable Polyline
                    Polyline polyline = mMap.addPolyline(polylineOptions);

                }

                return true;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public class getRoute implements Runnable{
        public getRoute(){

        }

        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/map/route")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (response.isSuccessful()){
                            Gson gson = new Gson();
                            Route route = gson.fromJson(response.body().charStream(), Route.class);

                            Log.d("TAG-old", "onResponse" + route);
                            sendMsg(route);
                        }
                    }
                }
            });
        }
        private void sendMsg(Route route){
            Bundle bundle = new Bundle();
            bundle.putSerializable("route", route);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }







    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/*
        // Add a marker in Sydney and move the camera
        LatLng clt = new LatLng(35.2271, -80.8431);
        mMap.addMarker(new MarkerOptions()
                .position(clt)
                .title("Marker in Charlotte"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(clt));*/

    }
}