package com.shubham.umerapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class helperFunctions {

    public String getCurrentDate()
    {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    public String getCurrentTime(){
        Date time = Calendar.getInstance().getTime();
        return time.toString();
    }

}
