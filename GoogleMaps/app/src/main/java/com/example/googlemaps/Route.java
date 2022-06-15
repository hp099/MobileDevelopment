package com.example.googlemaps;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    String title;
    ArrayList<Coordinates> path;

    @Override
    public String toString() {
        return "Route{" +
                "title='" + title + '\'' +
                ", path=" + path +
                '}';
    }
}
