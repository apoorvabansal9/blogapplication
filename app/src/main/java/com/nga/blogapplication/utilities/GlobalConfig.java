package com.nga.blogapplication.utilities;

import android.text.format.DateUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GlobalConfig {

    public static String convertRelativeTime(String strDate) {
        String someDaysAgo = "";
        long dateInMilliSecs, nowDateInMillis;

        try {
            SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DATE_TIME_FORMAT.setTimeZone(TimeZone.getDefault());
            DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

            Date original = DATE_TIME_FORMAT.parse(strDate);

//        FuzzyDateFormatter fuzzFormat = FuzzyDateFormat.getInstance();
//        someDaysAgo = fuzzFormat.formatDistance(original);


            dateInMilliSecs = original.getTime();
//        FuzzyDateFormatter fuzzFormat = FuzzyDateFormat.getInstance();
//        someDaysAgo = fuzz
            nowDateInMillis = new Date().getTime();

            someDaysAgo = DateUtils.getRelativeTimeSpanString(dateInMilliSecs, nowDateInMillis, DateUtils.FORMAT_ABBREV_RELATIVE).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return someDaysAgo;
    }
}
