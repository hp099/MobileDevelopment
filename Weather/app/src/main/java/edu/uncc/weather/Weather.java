package edu.uncc.weather;

import java.io.Serializable;
import java.util.ArrayList;

public class Weather implements Serializable {
    ArrayList<Wth> weather;
    String name, base;
    Sys sys;
    Clouds clouds;
    Main main;

    Wind wind;

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", sys=" + sys +
                ", clouds=" + clouds +
                ", main=" + main +
                ", weather=" + weather +
                ", wind=" + wind +
                '}';
    }
}
