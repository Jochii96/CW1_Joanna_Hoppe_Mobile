package com.example.cw1;



public class Weather {
    private String day;
    private String temperature;
    private String time;
    private String date;
    private String locationName; // Added property for location name



    private ThreeDayForecast threeDayForecast;

    // Constructor
    public Weather() {



    }

    // Getters and Setters for each property
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public ThreeDayForecast getThreeDayForecast() {
        return threeDayForecast;
    }

    public void setThreeDayForecast(ThreeDayForecast threeDayForecast) {
        this.threeDayForecast = threeDayForecast;
    }
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }




    // Optional: Override toString() for easy debugging/printing
    @Override
    public String toString() {
        return "Weather{" +
                "condition='" + day + '\'' +
                ", temperature='" + temperature + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
