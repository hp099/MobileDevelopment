package com.example.googledirections;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
    Harsh Patel
    HW 08
    Group11
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Place startPlace;
    private Place endPlace;
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    private LatLng middle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Places.initialize(getApplicationContext(), "AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0");

        PlacesClient placesClient = Places.createClient(this);


        findViewById(R.id.buttonGetDir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMap();
            }
        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap != null){
                    resetMap();
                }
            }
        });

        findViewById(R.id.buttonGas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (middle != null ){
                    gas();
                }
            }
        });

        findViewById(R.id.buttonRestaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (middle != null ){
                    res();
                }
            }
        });

        setupMap();
        startBox();
        endBox();

    }

    public void setupMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    public void startBox(){
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentStart);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                startPlace = place;
                updateMap();
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }

        });
    }

    public void endBox(){
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentEnd);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                endPlace = place;
                updateMap();
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }

        });
    }

    public void updateMap(){

        if (mMap != null){

            resetMap();

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            if(startPlace != null){
                mMap.addMarker(new MarkerOptions().position(startPlace.getLatLng()).title(startPlace.getName()));
                builder.include(startPlace.getLatLng());
            }

            if(endPlace != null){
                mMap.addMarker(new MarkerOptions().position(endPlace.getLatLng()).title(endPlace.getName()));
                builder.include(endPlace.getLatLng());
            }

            if((startPlace != null) && (endPlace != null)){
                getDirections();
            }

            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        }
    }

    public void resetMap(){
        mMap.clear();
    }

    public void getDirections(){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("directions")
                .addPathSegment("json")
                .addQueryParameter("origin", "place_id:" + startPlace.getId())
                .addQueryParameter("destination", "place_id:" + endPlace.getId())
                .addQueryParameter("key", "AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0")
                .build();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "Direction Error", Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()){
                    if (! response.isSuccessful()){
                        throw new IOException("Unexpected " + response);
                    }

                    JSONObject geoResponse = new JSONObject(responseBody.string());
                    String poly = geoResponse.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");

                    List<LatLng> routeList = PolyUtil.decode(poly);
                    middle = routeList.get(routeList.size() / 2);
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(routeList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.addPolyline(polylineOptions);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng clt = new LatLng(35.2271, -80.8431);
        /*mMap.addMarker(new MarkerOptions()
                .position(clt)
                .title("Marker in Charlotte"));*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clt, 10));

    }

    public void gas() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("nearbysearch")
                .addPathSegment("json")
                .addQueryParameter("location", middle.latitude + "," + middle.longitude)
                .addQueryParameter("type", "gas_station")
                .addQueryParameter("radius", "15000")
                .addQueryParameter("key", "AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0")
                .build();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "Nearby Places Error", Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()){
                    if (! response.isSuccessful()){
                        throw new IOException("Unexpected " + response);
                    }

                    JSONObject geoResponse = new JSONObject(responseBody.string());
                    JSONArray poly = geoResponse.getJSONArray("results");

                    Log.i("HELLO", geoResponse.toString());

                    for (int i = 0; i < (poly.length() - 1); i++) {
                        double lat = poly.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double lng = poly.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        LatLng loc = new LatLng(lat, lng);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions().position(loc));
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void res() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("nearbysearch")
                .addPathSegment("json")
                .addQueryParameter("location", middle.latitude + "," + middle.longitude)
                .addQueryParameter("type", "restaurant")
                .addQueryParameter("radius", "15000")
                .addQueryParameter("key", "AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0")
                .build();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "Nearby Places Error", Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()){
                    if (! response.isSuccessful()){
                        throw new IOException("Unexpected " + response);
                    }

                    JSONObject geoResponse = new JSONObject(responseBody.string());
                    JSONArray poly = geoResponse.getJSONArray("results");

                    Log.i("HELLO", geoResponse.toString());

                    for (int i = 0; i < (poly.length() - 1); i++) {
                        double lat = poly.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double lng = poly.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        LatLng loc = new LatLng(lat, lng);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions().position(loc));
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}