package edu.uncc.weather;

public class Sys {
    String country;

    @Override
    public String toString() {
        return "Sys{" +
                "country='" + country + '\'' +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
