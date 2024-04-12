package com.example.cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.concurrent.ConcurrentHashMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ViewPager2 viewPager2;


    private Map<String, String> locationIdToNameMap = new ConcurrentHashMap<>();

    private Map<String, ThreeDayForecast> forecastData = new HashMap<>();
    private Map<String, Weather> weatherData = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLocationIdToNameMap();
        viewPager = findViewById(R.id.viewPager);
        viewPager2 = findViewById(R.id.viewPager2);
        fetchWeatherForAllLocations();
        startAdditionalDataFetching();



    }

    private void populateLocationIdToNameMap() {

        locationIdToNameMap.put("2648579", "Glasgow");
        locationIdToNameMap.put("2643743", "London");
        locationIdToNameMap.put("5128581", "New York");
        locationIdToNameMap.put("287286", "Oman");
        locationIdToNameMap.put("934154", "Mauritius");
        locationIdToNameMap.put("1185241", "Bangladesh");
        locationIdToNameMap.put("2193733", "Auckland");
    }

    private void fetchWeatherForAllLocations() {
        for (String locationId : locationIdToNameMap.keySet()) {
            startProgress(locationId);
        }
    }

    private void startProgress(String locationId) {
        String url = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/" + locationId;
        new Thread(new Task(url, locationId)).start();
    }

    private class Task implements Runnable {
        private final String url;
        private final String locationId;

        Task(String url, String locationId) {
            this.url = url;
            this.locationId = locationId;
        }

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            try {
                URL aurl = new URL(url);
                URLConnection yc = aurl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
                in.close();
            } catch (IOException e) {
                Log.e("MyTag", "IOException in reading XML", e);
            }
            parseXML(result.toString(), locationId);
        }

        private void parseXML(String xml, String locationId) {
            final Weather weather = new Weather();
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(xml));
                int eventType = xpp.getEventType();
                boolean insideItem = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if ("item".equals(xpp.getName())) {
                            insideItem = true;
                        } else if (insideItem && "title".equals(xpp.getName())) {
                            // Extracting weather condition from the title
                            String titleText = xpp.nextText();
                            String day = titleText.split(" - ")[0].trim();
                            weather.setDay(day);

                            String[] parts = titleText.split(" - ")[1].split(":", 3);
                            String time = parts[0] + ":" + parts[1].trim().split(" ")[0];
                            weather.setTime(time);

                            String[] temperaturePart = titleText.split("Â°C")[0].split(",");  // Isolate the part ending with the temperature
                            String temperature = temperaturePart[temperaturePart.length - 1].split(" ")[1].trim(); // Extract "16"
                            weather.setTemperature(temperature);



                        } else if (insideItem && "pubDate".equals(xpp.getName())) {
                            String pubDateFullText = xpp.nextText();
                            // Extracting publication date from the pubDate tag
                            String[] dateParts = pubDateFullText.split(" ");
                            String date = dateParts[1] + " " + dateParts[2] + " " + dateParts[3];
                            weather.setDate(date);
                        }
                        // Extend with additional parsing rules as needed
                    } else if (eventType == XmlPullParser.END_TAG && "item".equals(xpp.getName())) {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                Log.e("MyTag", "Parsing error", e);
            }

            final String locationName = locationIdToNameMap.get(locationId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateWeatherData(locationName, weather);
                }
            });
        }

        private void updateWeatherData(String locationName, Weather weather) {
            if (locationName != null && weather != null) {
                weatherData.put(locationName, weather);
            }

            if (weatherData.size() == locationIdToNameMap.size()) {
                updateViewPager();
            }
        }



        // Check if all weather data is fetched and parsed

    }



        // Existing fields and methods...

        // New method to start fetching additional data
        private void startAdditionalDataFetching() {
            for (String locationId : locationIdToNameMap.keySet()) {
                String newUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + locationId;
                new Thread(new AdditionalDataTask(newUrl, locationId)).start();
            }
        }

        // AdditionalDataTask class
        private class AdditionalDataTask implements Runnable {
            private final String url;
            private final String locationId;



            AdditionalDataTask(String url, String locationId) {
                this.url = url;
                this.locationId = locationId;
            }

            @Override
            public void run() {
                StringBuilder result = new StringBuilder();
                try {
                    URL aurl = new URL(url);
                    URLConnection yc = aurl.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result.append(inputLine);
                    }
                    in.close();
                } catch (IOException e) {
                    Log.e("MyTag", "IOException in reading XML", e);
                }
                // Assuming you directly use the fetched data
                parseThreeDayForecastXML(result.toString(), locationId);
            }

            private void parseThreeDayForecastXML(String xml, String locationId) {
                final ThreeDayForecast forecast = new ThreeDayForecast();

                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(xml));
                    int eventType = xpp.getEventType();
                    boolean insideItem = false;
                    int dayCounter = 0;

                    Log.d("MyTag", "Starting XML Parsing");

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            Log.d("MyTag", "Start tag: " + xpp.getName());

                            if ("item".equals(xpp.getName())) {
                                insideItem = true;
                                dayCounter++;
                                Log.d("MyTag", "Found an item. Day counter: " + dayCounter);
                            } else if (insideItem) {
                                if ("title".equals(xpp.getName())) {
                                    String titleText = xpp.nextText();
                                    Log.d("MyTag", "Title text: " + titleText);

                                    // Parsing logic for "title" tag
                                    String dayFor = titleText.split(":")[0].trim();
                                    String tempMin = titleText.contains("Minimum Temperature") ? titleText.split("Minimum Temperature:")[1].split("°")[0].trim() : "N/A";
                                    String tempMax = titleText.contains("Maximum Temperature") ? titleText.split("Maximum Temperature:")[1].split("°")[0].trim() : "N/A";
                                    String[] conPart = titleText.split("°C");
                                    String[] commaParts = conPart[0].split(",");
                                    String conSegment = commaParts[commaParts.length - 2];
                                    String conDay = conSegment.split(":")[1].split("\\(")[0].trim();

                                    // Assigning values based on dayCounter
                                    switch (dayCounter) {
                                        case 1:
                                            forecast.setDayDay1(dayFor);
                                            forecast.setTempMinDay1(tempMin);
                                            forecast.setTempMaxDay1(tempMax);
                                            forecast.setConDay1(conDay);
                                            break;
                                        case 2:
                                            forecast.setDayDay2(dayFor);
                                            forecast.setTempMinDay2(tempMin);
                                            forecast.setTempMaxDay2(tempMax);
                                            forecast.setConDay2(conDay);
                                            break;
                                        case 3:
                                            forecast.setDayDay3(dayFor);
                                            forecast.setTempMinDay3(tempMin);
                                            forecast.setTempMaxDay3(tempMax);
                                            forecast.setConDay3(conDay);
                                            break;
                                    }
                                } else if ("description".equals(xpp.getName())) {
                                    String descriptionText = xpp.nextText();
                                    Log.d("MyTag", "Description text: " + descriptionText);

                                    // Parsing logic for "description" tag
                                    String windSpeed = "", pressure = "", humidity = "", uvRisk = "";
                                    for (String part : descriptionText.split(", ")) {
                                        if (part.contains("Wind Speed")) {
                                            windSpeed = part.split(": ")[1];
                                        } else if (part.contains("Pressure")) {
                                            pressure = part.split(": ")[1];
                                        } else if (part.contains("Humidity")) {
                                            humidity = part.split(": ")[1];
                                        } else if (part.contains("UV Risk")) {
                                            uvRisk = part.split(": ")[1];
                                        }
                                    }

                                    // Update the forecast object based on dayCounter
                                    switch (dayCounter) {
                                        case 1:
                                            forecast.setWindSpeedDay1(windSpeed);
                                            forecast.setPressureDay1(pressure);
                                            forecast.setHumidityDay1(humidity);
                                            forecast.setUvRiskDay1(uvRisk);
                                            break;
                                        case 2:
                                            forecast.setWindSpeedDay2(windSpeed);
                                            forecast.setPressureDay2(pressure);
                                            forecast.setHumidityDay2(humidity);
                                            forecast.setUvRiskDay2(uvRisk);
                                            break;
                                        case 3:
                                            forecast.setWindSpeedDay3(windSpeed);
                                            forecast.setPressureDay3(pressure);
                                            forecast.setHumidityDay3(humidity);
                                            forecast.setUvRiskDay3(uvRisk);
                                            break;
                                    }
                                }
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            Log.d("MyTag", "End tag: " + xpp.getName());
                            if ("item".equals(xpp.getName())) {
                                insideItem = false;
                                Log.d("MyTag", "Ending an item.");
                            }
                        }
                        eventType = xpp.next();
                    }
                    Log.d("MyTag", "Finished XML Parsing");
                } catch (Exception e) {
                    Log.e("MyTag", "Parsing error", e);
                }





            final String locationName = locationIdToNameMap.get(locationId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateForecastData(locationName, forecast);
                    }
                });
            }

            private void updateForecastData(String locationName, ThreeDayForecast forecast) {
                if (locationName != null && forecast != null) {
                    forecastData.put(locationName, forecast);
                }

                if (forecastData.size() == locationIdToNameMap.size()) {
                    updateViewPager();
                }
            }}
            // Method to handle new data (to be implemented)
        private void updateViewPager() {
            List<String> locationNames = new ArrayList<>(locationIdToNameMap.values());
            LocationAdapter adapter = new LocationAdapter(this, locationNames, weatherData, forecastData);
            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false);
        }

    }




