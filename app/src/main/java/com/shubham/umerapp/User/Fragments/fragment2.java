package com.shubham.umerapp.User.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shubham.umerapp.R;
import com.shubham.umerapp.Utils.helperFunctions;

public class fragment2 extends Fragment {
    TextView day,month;
    public String rawDate, df;
    String s_year, s_month, s_day, s_hour, s_min;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        day = view.findViewById(R.id.txtday);
        month = view.findViewById(R.id.txtmonth);

        helperFunctions hp = new helperFunctions();
        rawDate = hp.getCurrentTime();

        s_year = rawDate.substring(30, 34);
        s_month = rawDate.substring(4,7);
        s_day = rawDate.substring(8,10);
        s_hour = rawDate.substring(11,13);
        s_min = rawDate.substring(14,16);
        day.setText(s_day);
        month.setText(s_month);
        day.setTextSize(96);
        month.setTextSize(48);

        return view;
    }
}