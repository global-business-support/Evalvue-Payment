package com.subscription.unixtime;

import java.util.Date;

public class UnixTimeConverter {

    public static Date convertUnixTimeToDate(Object unixTime) throws Exception {
        if (unixTime.equals(null)) {
            return null;
        }
        long timestamp = Long.parseLong(unixTime.toString()) * 1000L;
        Date date = new Date(timestamp);
        return date;

    }
}
