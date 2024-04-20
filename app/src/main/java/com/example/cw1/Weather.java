/* Weather.java Joanna Hoppe, StudentId: S2337692 */
package com.example.cw1;


/* Joanna Hoppe, StudentId: S2337692 */

    // Class to represent weather details for a specific location
public class Weather {

/* Joanna Hoppe, StudentId: S2337692 */

    // Fields to store weather conditions and date/time info
    private String day;
    private String temperature;
    private String time;
    private String date;
    private String locationName;


        // Associated three-day weather forecast
    private ThreeDayForecast threeDayForecast;

        // Default constructor
    public Weather() {

    }

        // Getter for day condition
        public String getDay() {
            return day;
        }

        // Setter for day condition
        public void setDay(String day) {
            this.day = day;
        }

        // Getter for temperature
        public String getTemperature() {
            return temperature;
        }

        // Setter for temperature
        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        // Getter for time of weather data
        public String getTime() {
            return time;
        }

        // Setter for time of weather data
        public void setTime(String time) {
            this.time = time;
        }

        // Getter for date of weather data
        public String getDate() {
            return date;
        }

        // Setter for date of weather data
        public void setDate(String date) {
            this.date = date;
        }

        // Getter for the associated three-day forecast
        public ThreeDayForecast getThreeDayForecast() {
            return threeDayForecast;
        }

        // Setter for the associated three-day forecast
        public void setThreeDayForecast(ThreeDayForecast threeDayForecast) {
            this.threeDayForecast = threeDayForecast;
        }

        // Getter for location name
        public String getLocationName() {
            return locationName;
        }

        // Setter for location name
        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        // Method to return a string representation of the Weather object
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