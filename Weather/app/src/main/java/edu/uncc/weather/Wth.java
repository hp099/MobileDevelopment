package edu.uncc.weather;

public class Wth {
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Wth{" +
                "description='" + description + '\'' +
                '}';
    }
}
