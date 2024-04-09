package com.example.cw1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

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

    public static LocationFragment newInstance(String locationName, String day, String temperature, String time, String date,
                                               String dayDay1, String dayDay2, String dayDay3, String minTempDay1, String minTempDay2,
                                               String minTempDay3, String maxTempDay1, String maxTempDay2, String maxTempDay3,
                                               String conDay1, String conDay2, String conDay3) {

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

        TextView conDay1TextView = view.findViewById(R.id.conFor1);
        TextView conDay2TextView = view.findViewById(R.id.conFor2);
        TextView conDay3TextView = view.findViewById(R.id.conFor3);

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

            conDay1TextView.setText(conDay1);
            conDay2TextView.setText(conDay2);
            conDay3TextView.setText(conDay3);



        } else {
            Log.d("LocationFragment", "Arguments are null");
        }
        return view;
    }
}