package com.example.cw1;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
import java.util.Map;


public class LocationAdapter extends FragmentStateAdapter {
    private final List<String> locationNames;
    private final Map<String, Weather> weatherData;
    private final Map<String, ThreeDayForecast> forecastData;

    public LocationAdapter(FragmentActivity fragmentActivity, List<String> locationNames, Map<String, Weather> weatherData, Map<String, ThreeDayForecast> forecastData) {
        super(fragmentActivity);
        this.locationNames = locationNames;
        this.weatherData = weatherData;
        this.forecastData = forecastData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int adjustedPosition = position % locationNames.size();
        String locationName = locationNames.get(adjustedPosition);
        Weather weather = weatherData.getOrDefault(locationName, new Weather());
        ThreeDayForecast forecast = forecastData.getOrDefault(locationName, new ThreeDayForecast());

        return LocationFragment.newInstance(
                locationName,
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