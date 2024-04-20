/* MainActivity.java Joanna Hoppe, StudentId: S2337692 */
package com.example.cw1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager2.widget.ViewPager2;
import java.util.concurrent.ConcurrentHashMap;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import java.util.Calendar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

/* Joanna Hoppe, StudentId: S2337692 */

// Main activity class for handling the application's main user interface.
public class MainActivity extends AppCompatActivity {

/* Joanna Hoppe, StudentId: S2337692 */

        private ViewPager2 viewPager; // Component to swipe through pages
        private ViewPager2 viewPager2; // Second ViewPager, usage not shown in this snippet
        private Handler handler = new Handler(Looper.getMainLooper()); // Handler for managing threads
        private Runnable dataUpdateChecker; // Runnable task for scheduled updates
        private Spinner locationSpinner; // Dropdown menu for selecting locations
        private Map<String, String> locationIdToNameMap = new HashMap<>(); // Maps location IDs to names

        private Map<String, ThreeDayForecast> forecastData = new HashMap<>(); // Stores three-day forecast data by location
        private Map<String, Weather> weatherData = new ConcurrentHashMap<>(); // Concurrent map to store weather data by location

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main); // Setting the content view from a layout resource
            locationSpinner = findViewById(R.id.locationSpinner); // Finding the spinner by its ID
            populateLocationIdToNameMap(); // Populating the map with location IDs and names

            // Setting up the spinner with location names
            List<String> locationNames = new ArrayList<>();
            locationNames.add("Select Location"); // Adding default selection
            locationNames.addAll(locationIdToNameMap.values()); // Adding all location names

            // Creating an ArrayAdapter for the spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locationSpinner.setAdapter(adapter);
            locationSpinner.setSelection(0); // Setting initial selection

            // Setting an item selected listener for the spinner
            locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0 && viewPager != null) {
                        viewPager.setCurrentItem(position - 1); // Setting the corresponding page in the ViewPager
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Optional handling if nothing is selected
                }
            });

            viewPager = findViewById(R.id.viewPager); // Finding the ViewPager by its ID
            viewPager2 = findViewById(R.id.viewPager2); // Finding the second ViewPager
            fetchWeatherForAllLocations(); // Fetching initial weather data for all locations
            startAdditionalDataFetching(); // Starting additional data fetching tasks

            setupDataUpdateChecker(); // Setting up a task to check for updates at specific times
        }

        // Method to populate the location ID to name map
        private void populateLocationIdToNameMap() {
            locationIdToNameMap.put("2648579", "Glasgow");
            locationIdToNameMap.put("2643743", "London");
            locationIdToNameMap.put("5128581", "New York");
            locationIdToNameMap.put("287286", "Oman");
            locationIdToNameMap.put("934154", "Mauritius");
            locationIdToNameMap.put("1185241", "Bangladesh");
        }

        // Setup periodic checking for data updates
        private void setupDataUpdateChecker() {
            dataUpdateChecker = new Runnable() {
                @Override
                public void run() {
                    Calendar now = Calendar.getInstance();
                    int hour = now.get(Calendar.HOUR_OF_DAY);
                    int minute = now.get(Calendar.MINUTE);
                    // Check if the current time is 8 AM or 8 PM
                    if ((hour == 8 || hour == 20) && minute == 0) {
                        fetchWeatherForAllLocations(); // Fetch weather data if conditions are met
                    }
                    // Post a delay for the next check
                    handler.postDelayed(this, 3600000); // 1 hour delay
                }
            };
            // Initial delay setup to synchronize with the top of the next minute
            handler.postDelayed(dataUpdateChecker, 60000 - (Calendar.getInstance().get(Calendar.SECOND) * 1000));
        }

        // Method to fetch weather data for all locations
        private void fetchWeatherForAllLocations() {
            for (String locationId : locationIdToNameMap.keySet()) {
                startProgress(locationId); // Start fetching data for each location
            }
        }

        // Start a background thread to fetch data from a URL
        private void startProgress(String locationId) {
            String url = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/" + locationId;
            new Thread(new Task(url, locationId)).start(); // Start thread with the URL and location ID
        }

        // Task class to handle fetching and parsing XML data
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
                        result.append(inputLine); // Append each line of the response
                    }
                    in.close();
                } catch (IOException e) {
                    Log.e("MyTag", "IOException in reading XML", e); // Log errors if any
                }
                parseXML(result.toString(), locationId); // Parse the accumulated string as XML
            }

            // Method to parse XML data and update the weather object
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

                                String titleText = xpp.nextText();
                                String day = titleText.split(" - ")[0].trim();
                                weather.setDay(day);

                                String[] parts = titleText.split(" - ")[1].split(":", 3);
                                String time = parts[0] + ":" + parts[1].trim().split(" ")[0];
                                weather.setTime(time);

                                String[] temperaturePart = titleText.split("Â°C")[0].split(",");
                                String temperature = temperaturePart[temperaturePart.length - 1].split(" ")[1].trim();
                                weather.setTemperature(temperature);

                            } else if (insideItem && "pubDate".equals(xpp.getName())) {
                                String pubDateFullText = xpp.nextText();

                                String[] dateParts = pubDateFullText.split(" ");
                                String date = dateParts[1] + " " + dateParts[2] + " " + dateParts[3];
                                weather.setDate(date);
                            }

                        } else if (eventType == XmlPullParser.END_TAG && "item".equals(xpp.getName())) {
                            insideItem = false;
                        }
                        eventType = xpp.next();
                    }
                } catch (Exception e) {
                    Log.e("MyTag", "Parsing error", e); // Log parsing errors
                }

                final String locationName = locationIdToNameMap.get(locationId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateWeatherData(locationName, weather); // Update UI with new data
                    }
                });
            }

            // Method to update weather data in the main thread
            private void updateWeatherData(String locationName, Weather weather) {
                if (locationName != null && weather != null) {
                    weatherData.put(locationName, weather);
                }

                if (weatherData.size() == locationIdToNameMap.size()) {
                    updateViewPager(); // Update ViewPager if all data is fetched
                }
            }
        }

        // Method to fetch additional forecast data
        private void startAdditionalDataFetching() {
            for (String locationId : locationIdToNameMap.keySet()) {
                String newUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + locationId;
                new Thread(new AdditionalDataTask(newUrl, locationId)).start(); // Start thread for each location
            }
        }

        // Task class for fetching and parsing three-day forecast data
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
                        result.append(inputLine); // Accumulate response data
                    }
                    in.close();
                } catch (IOException e) {
                    Log.e("MyTag", "IOException in reading XML", e); // Log errors if any
                }

                parseThreeDayForecastXML(result.toString(), locationId); // Parse the result as XML
            }

            // Method to parse three-day forecast data
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
                    Log.e("MyTag", "Parsing error", e); // Log parsing errors
                }

                final String locationName = locationIdToNameMap.get(locationId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateForecastData(locationName, forecast); // Update UI with new forecast data
                    }
                });
            }

            // Method to update forecast data in the main thread
            private void updateForecastData(String locationName, ThreeDayForecast forecast) {
                if (locationName != null && forecast != null) {
                    forecastData.put(locationName, forecast); // Update forecast data map
                }

                if (forecastData.size() == locationIdToNameMap.size()) {
                    updateViewPager(); // Update ViewPager if all data is fetched
                }
            }
        }

        // Method to update the ViewPager with the latest data
        private void updateViewPager() {
            List<String> locationNames = new ArrayList<>(locationIdToNameMap.values());
            LocationAdapter adapter = new LocationAdapter(this, locationNames, weatherData, forecastData);
            viewPager.setAdapter(adapter); // Set adapter for ViewPager

            viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false); // Set initial item for infinite scroll illusion
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(dataUpdateChecker); // Clean up to avoid memory leaks
        }

    }



