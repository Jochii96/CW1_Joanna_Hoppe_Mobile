package com.example.cw1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class LocationFragment extends Fragment {
    private static final String ARG_LOCATION_NAME = "locationName";
    private static final String ARG_DAY_DAY = "day";
    private static final String ARG_TEMPERATURE = "temperature";
    private static final String ARG_TIME = "time";
    private static final String ARG_DATE = "date";
    // Additional arguments for day forecasts
    private static final String ARG_DAY_DAY1 = "dayDay1";
    private static final String ARG_DAY_DAY2 = "dayDay2";
    private static final String ARG_DAY_DAY3 = "dayDay3";

    private static final String ARG_Min_Temp_DAY1 = "minTempDay1";
    private static final String ARG_Min_Temp_DAY2 = "minTempDay2";
    private static final String ARG_Min_Temp_DAY3 = "minTempDay3";

    private static final String ARG_Max_Temp_DAY1 = "maxTempDay1";
    private static final String ARG_Max_Temp_DAY2 = "maxTempDay2";
    private static final String ARG_Max_Temp_DAY3 = "maxTempDay3";

    private static final String ARG_CON_DAY1 = "conDay1";
    private static final String ARG_CON_DAY2 = "conDay2";
    private static final String ARG_CON_DAY3 = "conDay3";

    private static final String ARG_WIND_SPEED_DAY1 = "windSpeedDay1";
    private static final String ARG_WIND_SPEED_DAY2 = "windSpeedDay2";
    private static final String ARG_WIND_SPEED_DAY3 = "windSpeedDay3";

    private static final String ARG_PRESSURE_DAY1 = "pressureDay1";
    private static final String ARG_PRESSURE_DAY2 = "pressureDay2";
    private static final String ARG_PRESSURE_DAY3 = "pressureDay3";

    private static final String ARG_HUMIDITY_DAY1 = "humidityDay1";
    private static final String ARG_HUMIDITY_DAY2 = "humidityDay2";
    private static final String ARG_HUMIDITY_DAY3 = "humidityDay3";

    private static final String ARG_UV_RISK_DAY1 = "uvRiskDay1";
    private static final String ARG_UV_RISK_DAY2 = "uvRiskDay2";
    private static final String ARG_UV_RISK_DAY3 = "uvRiskDay3";




    public static LocationFragment newInstance(String locationName, String day, String temperature, String time, String date,
                                               String dayDay1, String dayDay2, String dayDay3,
                                               String minTempDay1, String minTempDay2, String minTempDay3,
                                               String maxTempDay1, String maxTempDay2, String maxTempDay3,
                                               String conDay1, String conDay2, String conDay3,
                                               String windSpeedDay1, String windSpeedDay2, String windSpeedDay3,
                                               String pressureDay1, String pressureDay2, String pressureDay3,
                                               String humidityDay1, String humidityDay2, String humidityDay3,
                                               String uvRiskDay1, String uvRiskDay2, String uvRiskDay3) {

        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_NAME, locationName);
        args.putString(ARG_DAY_DAY, day);
        args.putString(ARG_TEMPERATURE, temperature);
        args.putString(ARG_TIME, time);
        args.putString(ARG_DATE, date);

        args.putString(ARG_DAY_DAY1, dayDay1);
        args.putString(ARG_DAY_DAY2, dayDay2);
        args.putString(ARG_DAY_DAY3, dayDay3);

        args.putString(ARG_Min_Temp_DAY1, minTempDay1);
        args.putString(ARG_Min_Temp_DAY2, minTempDay2);
        args.putString(ARG_Min_Temp_DAY3, minTempDay3);

        args.putString(ARG_Max_Temp_DAY1, maxTempDay1);
        args.putString(ARG_Max_Temp_DAY2, maxTempDay2);
        args.putString(ARG_Max_Temp_DAY3, maxTempDay3);

        args.putString(ARG_CON_DAY1, conDay1);
        args.putString(ARG_CON_DAY2, conDay2);
        args.putString(ARG_CON_DAY3, conDay3);

        args.putString(ARG_WIND_SPEED_DAY1, windSpeedDay1);
        args.putString(ARG_WIND_SPEED_DAY2, windSpeedDay2);
        args.putString(ARG_WIND_SPEED_DAY3, windSpeedDay3);

        args.putString(ARG_PRESSURE_DAY1, pressureDay1);
        args.putString(ARG_PRESSURE_DAY2, pressureDay2);
        args.putString(ARG_PRESSURE_DAY3, pressureDay3);

        args.putString(ARG_HUMIDITY_DAY1, humidityDay1);
        args.putString(ARG_HUMIDITY_DAY2, humidityDay2);
        args.putString(ARG_HUMIDITY_DAY3, humidityDay3);

        args.putString(ARG_UV_RISK_DAY1, uvRiskDay1);
        args.putString(ARG_UV_RISK_DAY2, uvRiskDay2);
        args.putString(ARG_UV_RISK_DAY3, uvRiskDay3);



        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        TextView locationTextView = view.findViewById(R.id.locationName);
        TextView dateTextView = view.findViewById(R.id.dateObs);
        TextView timeTextView = view.findViewById(R.id.timeObs);
        TextView dayTextView = view.findViewById(R.id.dayObs);
        TextView temperatureTextView = view.findViewById(R.id.tempObs);

        TextView dayDay1TextView = view.findViewById(R.id.dayFor1);
        TextView dayDay2TextView = view.findViewById(R.id.dayFor2);
        TextView dayDay3TextView = view.findViewById(R.id.dayFor3);

        TextView minTempDay1View = view.findViewById(R.id.tempMin1);
        TextView minTempDay2View = view.findViewById(R.id.tempMin2);
        TextView minTempDay3View = view.findViewById(R.id.tempMin3);

        TextView maxTempDay1View = view.findViewById(R.id.tempMax1);
        TextView maxTempDay2View = view.findViewById(R.id.tempMax2);
        TextView maxTempDay3View = view.findViewById(R.id.tempMax3);

        //TextView conDay1TextView = view.findViewById(R.id.conFor1);
        //TextView conDay2TextView = view.findViewById(R.id.conFor2);
        //TextView conDay3TextView = view.findViewById(R.id.conFor3);

        ImageView imageView1 = view.findViewById(R.id.viewDay1);
        ImageView imageView2 = view.findViewById(R.id.viewDay2);
        ImageView imageView3 = view.findViewById(R.id.viewDay3);
        ImageView imageView5 = view.findViewById(R.id.imageView5);

        TextView windSpeedDay1View = view.findViewById(R.id.windDay1);
        TextView windSpeedDay2View = view.findViewById(R.id.windDay2);
        TextView windSpeedDay3View = view.findViewById(R.id.windDay3);

        TextView pressureDay1View = view.findViewById(R.id.pressureDay1);
        TextView pressureDay2View = view.findViewById(R.id.pressureDay2);
        TextView pressureDay3View = view.findViewById(R.id.pressureDay3);

        TextView humidityDay1View = view.findViewById(R.id.humidityDay1);
        TextView humidityDay2View = view.findViewById(R.id.humidityDay2);
        TextView humidityDay3View = view.findViewById(R.id.humidityDay3);

        TextView uvRiskDay1View = view.findViewById(R.id.uvRiskDay1);
        TextView uvRiskDay2View = view.findViewById(R.id.uvRiskDay2);
        TextView uvRiskDay3View = view.findViewById(R.id.uvRiskDay3);

        ImageView imageView6 = view.findViewById(R.id.wind1);
        ImageView imageView7 = view.findViewById(R.id.wind2);
        ImageView imageView8 = view.findViewById(R.id.wind3);

        ImageView imageView10 = view.findViewById(R.id.pr1);
        ImageView imageView11 = view.findViewById(R.id.pr2);
        ImageView imageView12 = view.findViewById(R.id.pr3);

        ImageView imageView14 = view.findViewById(R.id.hum1);
        ImageView imageView15 = view.findViewById(R.id.hum2);
        ImageView imageView16 = view.findViewById(R.id.hum3);

        ImageView imageView17 = view.findViewById(R.id.uv1);
        ImageView imageView18 = view.findViewById(R.id.uv2);
        ImageView imageView19 = view.findViewById(R.id.uv3);




        Bundle args = getArguments();
        if (args != null) {
            String locationName = args.getString(ARG_LOCATION_NAME, "N/A");
            String day = args.getString(ARG_DAY_DAY, "N/A");
            String temperature = args.getString(ARG_TEMPERATURE, "N/A");
            String time = args.getString(ARG_TIME, "N/A");
            String date = args.getString(ARG_DATE, "N/A");

            String dayDay1 = args.getString(ARG_DAY_DAY1, "N/A");
            String dayDay2 = args.getString(ARG_DAY_DAY2, "N/A");
            String dayDay3 = args.getString(ARG_DAY_DAY3, "N/A");

            String minTempDay1 = args.getString(ARG_Min_Temp_DAY1, "N/A");
            String minTempDay2 = args.getString(ARG_Min_Temp_DAY2, "N/A");
            String minTempDay3 = args.getString(ARG_Min_Temp_DAY3, "N/A");

            String maxTempDay1 = args.getString(ARG_Max_Temp_DAY1, "N/A");
            String maxTempDay2 = args.getString(ARG_Max_Temp_DAY2, "N/A");
            String maxTempDay3 = args.getString(ARG_Max_Temp_DAY3, "N/A");

            String conDay1 = args.getString(ARG_CON_DAY1, "N/A");
            String conDay2 = args.getString(ARG_CON_DAY2, "N/A");
            String conDay3 = args.getString(ARG_CON_DAY3, "N/A");

            String windSpeedDay1= args.getString(ARG_WIND_SPEED_DAY1, "N/A");
            String windSpeedDay2 = args.getString(ARG_WIND_SPEED_DAY2, "N/A");
            String windSpeedDay3 = args.getString(ARG_WIND_SPEED_DAY3, "N/A");

            String pressureDay1 = args.getString(ARG_PRESSURE_DAY1, "N/A");
            String pressureDay2 = args.getString(ARG_PRESSURE_DAY2, "N/A");
            String pressureDay3 = args.getString(ARG_PRESSURE_DAY3, "N/A");

            String humidityDay1 = args.getString(ARG_HUMIDITY_DAY1, "N/A");
            String humidityDay2 = args.getString(ARG_HUMIDITY_DAY2, "N/A");
            String humidityDay3 = args.getString(ARG_HUMIDITY_DAY3, "N/A");

            String uvRiskDay1 = args.getString(ARG_UV_RISK_DAY1, "N/A");
            String uvRiskDay2 = args.getString(ARG_UV_RISK_DAY2, "N/A");
            String uvRiskDay3 = args.getString(ARG_UV_RISK_DAY3, "N/A");

            // Log the values
            Log.d("LocationFragment", "Location Name: " + locationName);
            Log.d("LocationFragment", "Condition: " + day);
            Log.d("LocationFragment", "Temperature: " + temperature);
            Log.d("LocationFragment", "Time: " + time);
            Log.d("LocationFragment", "Date: " + date);
            Log.d("LocationFragment", "Condition Day 1: " + dayDay1);
            Log.d("LocationFragment", "Condition Day 2: " + dayDay2);
            Log.d("LocationFragment", "Condition Day 3: " + dayDay3);

            locationTextView.setText(locationName);
            dateTextView.setText(date);
            timeTextView.setText(time);
            dayTextView.setText(day);
            temperatureTextView.setText(temperature);

            dayDay1TextView.setText(dayDay1);
            dayDay2TextView.setText(dayDay2);
            dayDay3TextView.setText(dayDay3);

            minTempDay1View.setText(minTempDay1);
            minTempDay2View.setText(minTempDay2);
            minTempDay3View.setText(minTempDay3);

            maxTempDay1View.setText(maxTempDay1);
            maxTempDay2View.setText(maxTempDay2);
            maxTempDay3View.setText(maxTempDay3);

            windSpeedDay1View.setText(windSpeedDay1);
            windSpeedDay2View.setText(windSpeedDay2);
            windSpeedDay3View.setText(windSpeedDay3);

            pressureDay1View.setText(pressureDay1);
            pressureDay2View.setText(pressureDay2);
            pressureDay3View.setText(pressureDay3);

            humidityDay1View.setText(humidityDay1);
            humidityDay2View.setText(humidityDay2);
            humidityDay3View.setText(humidityDay3);

            uvRiskDay1View.setText(uvRiskDay1);
            uvRiskDay2View.setText(uvRiskDay2);
            uvRiskDay3View.setText(uvRiskDay3);

            //conDay1TextView.setText(conDay1);
            //conDay2TextView.setText(conDay2);
            //conDay3TextView.setText(conDay3);



            if ((conDay1.equals("Sunny") || conDay1.equals("Clear Sky")) && dayDay1.equals("Today")) {
                imageView5.setImageResource(R.drawable.day_clear);
            } else if ((conDay1.equals("Sunny") || conDay1.equals("Clear Sky")) && dayDay1.equals("Tonight")) {
                imageView5.setImageResource(R.drawable.night_clear);
            } else if ((conDay1.equals("Sunny Intervals") || conDay1.equals("Partly Cloudy") || conDay1.equals("Light Cloud")) && dayDay1.equals("Today")) {
                imageView5.setImageResource(R.drawable.day_partial_cloud);
            } else if ((conDay1.equals("Sunny Intervals") || conDay1.equals("Partly Cloudy") || conDay1.equals("Light Cloud")) && dayDay1.equals("Tonight")) {
                imageView5.setImageResource(R.drawable.night_partial_cloud);
            } else if ((conDay1.equals("Light Rain") || conDay1.equals("Light Rain Showers") || conDay1.equals("Drizzle")) && dayDay1.equals("Today")) {
                imageView5.setImageResource(R.drawable.day_rain);
            } else if ((conDay1.equals("Light Rain") || conDay1.equals("Light Rain Showers")|| conDay1.equals("Drizzle")) && dayDay1.equals("Tonight")) {
                imageView5.setImageResource(R.drawable.night_rain);
            } else if ((conDay1.equals("Thundery Showers") || conDay1.equals("Thunderstorm")) && dayDay1.equals("Today")) {
                imageView5.setImageResource(R.drawable.day_rain_thunder);
            } else if ((conDay1.equals("Thundery Showers") || conDay1.equals("Thunderstorm")) && dayDay1.equals("Tonight")) {
                imageView5.setImageResource(R.drawable.night_rain_thunder);
            } else {
                // Default image or handling for other cases
                imageView5.setImageResource(R.drawable.rick_astley_rickroll);
            }

            if (conDay1.equals("Sunny") || conDay1.equals("Clear Sky"))  {
                imageView1.setImageResource(R.drawable.day_clear);
            } else if (conDay1.equals("Sunny Intervals") || conDay1.equals("Partly Cloudy") || conDay1.equals("Light Cloud")) {
                imageView1.setImageResource(R.drawable.day_partial_cloud);
            } else if (conDay1.equals("Light Rain") || conDay1.equals("Light Rain Showers")|| conDay1.equals("Drizzle")) {
                imageView1.setImageResource(R.drawable.rain);
            } else if (conDay1.equals("Thundery Showers")|| conDay1.equals("Thunderstorm")){
                imageView1.setImageResource(R.drawable.rain_thunder);
            } else if (conDay1.equals("Thick Cloud")) {
                imageView1.setImageResource(R.drawable.cloudy);
                imageView5.setImageResource(R.drawable.cloudy);
            } else if (conDay1.equals("Fog")) {
                imageView1.setImageResource(R.drawable.fog);
                imageView5.setImageResource(R.drawable.fog);
            } else if (conDay1.equals("Mist")) {
                imageView1.setImageResource(R.drawable.mist);
                imageView5.setImageResource(R.drawable.mist);
            } else {
                // Default image or handling for other cases
                imageView1.setImageResource(R.drawable.rain_thunder);
            }

            if (conDay2.equals("Sunny") || conDay2.equals("Clear Sky")){
                imageView2.setImageResource(R.drawable.day_clear);
            } else if (conDay2.equals("Sunny Intervals") || conDay2.equals("Partly Cloudy") || conDay2.equals("Light Cloud")) {
                imageView2.setImageResource(R.drawable.day_partial_cloud);
            } else if (conDay2.equals("Light Rain") || conDay2.equals("Light Rain Showers")|| conDay2.equals("Drizzle")) {
                imageView2.setImageResource(R.drawable.rain);
            } else if (conDay2.equals("Thundery Showers")|| conDay2.equals("Thunderstorm")) {
                imageView2.setImageResource(R.drawable.rain_thunder);
            } else if (conDay2.equals("Thick Cloud")) {
                imageView2.setImageResource(R.drawable.cloudy);
            } else if (conDay2.equals("Fog")) {
                imageView2.setImageResource(R.drawable.fog);
            } else {
                // Default image or handling for other cases
                Glide.with(this)
                        .asGif()
                        .load(R.drawable.rick_astley_rickroll) // make sure your GIF file is in the drawable folder
                        .into(imageView2);
            }

            if (conDay3.equals("Sunny") || conDay3.equals("Clear Sky")){
                imageView3.setImageResource(R.drawable.day_clear);
            } else if (conDay3.equals("Sunny Intervals") || conDay3.equals("Partly Cloudy") || conDay3.equals("Light Cloud")) {
                imageView3.setImageResource(R.drawable.day_partial_cloud);
            } else if (conDay3.equals("Light Rain") || conDay3.equals("Light Rain Showers")|| conDay3.equals("Drizzle")) {
                imageView3.setImageResource(R.drawable.rain);
            } else if (conDay3.equals("Thundery Showers")|| conDay3.equals("Thunderstorm")) {
                imageView3.setImageResource(R.drawable.rain_thunder);
            } else if (conDay3.equals("Thick Cloud")) {
                imageView3.setImageResource(R.drawable.cloudy);
            } else if (conDay3.equals("Fog")) {
                imageView3.setImageResource(R.drawable.fog);
            } else {
                // Default image or handling for other cases
                imageView3.setImageResource(R.drawable.rain_thunder);
            }



        } else {
            Log.d("LocationFragment", "Arguments are null");
        }

        imageView6.setImageResource(R.drawable.wind);
        imageView7.setImageResource(R.drawable.wind);
        imageView8.setImageResource(R.drawable.wind);

// Set pressure icons
        imageView10.setImageResource(R.drawable.barometer);
        imageView11.setImageResource(R.drawable.barometer);
        imageView12.setImageResource(R.drawable.barometer);

// Set humidity icons
        imageView14.setImageResource(R.drawable.humidity);
        imageView15.setImageResource(R.drawable.humidity);
        imageView16.setImageResource(R.drawable.humidity);

// Set UV icons
        imageView17.setImageResource(R.drawable.uv_protection);
        imageView18.setImageResource(R.drawable.uv_protection);
        imageView19.setImageResource(R.drawable.uv_protection);

        final View addDay1Layout = view.findViewById(R.id.addDay1);
        final View addDay2Layout = view.findViewById(R.id.addDay2);
        final View addDay3Layout = view.findViewById(R.id.addDay3);

        // Set the initial visibility to GONE
        addDay1Layout.setVisibility(View.GONE);
        addDay2Layout.setVisibility(View.GONE);
        addDay3Layout.setVisibility(View.GONE);

    view.findViewById(R.id.dayFor1).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Toggle visibility
            if (addDay1Layout.getVisibility() == View.VISIBLE) {
                addDay1Layout.setVisibility(View.GONE);
            } else {
                addDay1Layout.setVisibility(View.VISIBLE);
            }
        }
    });

    view.findViewById(R.id.dayFor2).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (addDay2Layout.getVisibility() == View.VISIBLE) {
                addDay2Layout.setVisibility(View.GONE);
            } else {
                addDay2Layout.setVisibility(View.VISIBLE);
            }
        }
    });

    view.findViewById(R.id.dayFor3).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (addDay3Layout.getVisibility() == View.VISIBLE) {
                addDay3Layout.setVisibility(View.GONE);
            } else {
                addDay3Layout.setVisibility(View.VISIBLE);
            }
        }
    });

    // Return the root view
    return view;
}

}