package com.subscription.unixtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UnixTimeConverter {

    public static Date convertUnixTimeToDate(Object unixTime) throws ParseException {
        if (unixTime.equals(null)) {
            return null;
        }
        System.out.println(unixTime);
        long timestamp = Long.parseLong(unixTime.toString()) * 1000L;
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());
        String localDateString = sdf.format(date);
        return sdf.parse(localDateString);

    }
}
