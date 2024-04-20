/* LocationAdapter.java Joanna Hoppe, StudentId: S2337692 */
package com.example.cw1;

// LocationAdapter.java - Manages the creation of fragments for different locations in a ViewPager2

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Joanna Hoppe, StudentId: S2337692 */
public class LocationAdapter extends FragmentStateAdapter {

/* Joanna Hoppe, StudentId: S2337692 */
// List of location names
private final List<String> locationNames;

    // Maps holding weather and forecast data keyed by location name
    private final Map<String, Weather> weatherData;
    private final Map<String, ThreeDayForecast> forecastData;

    // Map holding coordinates for each location
    private final Map<String, LatLng> locationCoordinates = new HashMap<>();

    // Constructor initializing with the fragment activity and data maps
    public LocationAdapter(FragmentActivity fragmentActivity, List<String> locationNames, Map<String, Weather> weatherData, Map<String, ThreeDayForecast> forecastData) {
        super(fragmentActivity);
        this.locationNames = locationNames;
        this.weatherData = weatherData;
        this.forecastData = forecastData;
        populateCoordinates();
    }
    // Populate the coordinates map with predefined locations
    private void populateCoordinates() {
        locationCoordinates.put("Glasgow", new LatLng(55.8642, -4.2518)); // Glasgow
        locationCoordinates.put("London", new LatLng(51.5074, -0.1278)); // London
        locationCoordinates.put("New York", new LatLng(40.7128, -74.0060)); // New York
        locationCoordinates.put("Oman", new LatLng(23.5880, 58.3829));   // Coordinates for Muscat, Oman
        locationCoordinates.put("Mauritius", new LatLng(-20.3484, 57.5522));  // Mauritius
        locationCoordinates.put("Bangladesh", new LatLng(23.6850, 90.3563));  // Coordinates for Dhaka, Bangladesh

    }
    // Create fragments for each position in the ViewPager2
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int adjustedPosition = position % locationNames.size();
        String locationName = locationNames.get(adjustedPosition);
        LatLng coordinates = locationCoordinates.get(locationName);

        // Log and handle if no coordinates are found for a location

        if (coordinates == null) {
            Log.e("LocationAdapter", "No coordinates found for location: " + locationName + ". Defaulting to 0,0");
            coordinates = new LatLng(0, 0); // Default to a known location or indicate error more clearly.
        } else {
            Log.d("LocationAdapter", "Coordinates for " + locationName + ": " + coordinates.latitude + ", " + coordinates.longitude);
        }

        double latitude = coordinates.latitude;
        double longitude = coordinates.longitude;

        // Log the coordinates being passed to the fragment

        Log.d("LocationAdapter", "Passing to fragment " + locationName + " with lat: " + latitude + ", long: " + longitude);

        // Retrieve weather and forecast data for the location

        Weather weather = weatherData.getOrDefault(locationName, new Weather());
        ThreeDayForecast forecast = forecastData.getOrDefault(locationName, new ThreeDayForecast());

        // Create a new instance of LocationFragment with the data

        return LocationFragment.newInstance(
                locationName,
                latitude,
                longitude,
                weather.getDay(),
                weather.getTemperature(),
                weather.getTime(),
                weather.getDate(),
                forecast.getSetDay1(),
                forecast.getSetDay2(),
                forecast.getSetDay3(),
                forecast.getTempMinDay1(),
                forecast.getTempMinDay2(),
                forecast.getTempMinDay3(),
                forecast.getTempMaxDay1(),
                forecast.getTempMaxDay2(),
                forecast.getTempMaxDay3(),
                forecast.getConDay1(),
                forecast.getConDay2(),
                forecast.getConDay3(),
                forecast.getWindSpeedDay1(),
                forecast.getWindSpeedDay2(),
                forecast.getWindSpeedDay3(),
                forecast.getPressureDay1(),
                forecast.getPressureDay2(),
                forecast.getPressureDay3(),
                forecast.getHumidityDay1(),
                forecast.getHumidityDay2(),
                forecast.getHumidityDay3(),
                forecast.getUvRiskDay1(),
                forecast.getUvRiskDay2(),
                forecast.getUvRiskDay3()
        );
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; // Set a large number to virtually allow infinite scrolling
    }
}