package com.shubham.umerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class fragment2 extends Fragment {
    TextView day,month;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        day = view.findViewById(R.id.txtday);
        month = view.findViewById(R.id.txtmonth);

        Bundle args = getArguments();
        if (args != null) {
            String Year = args.getString("Year");
            String Month = args.getString("Month");
            String Day = args.getString("Day");
            String Hour = args.getString("Hour");
            String Min = args.getString("Min");

            day.setText(Day);
            month.setText(Month);
        }else{
            day.setText("Unable to get Date");
            month.setText("Unable to get Month");
        }

        return view;
    }
}