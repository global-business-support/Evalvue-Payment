package com.subscription.unixtime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;

public class DateAfterOneMonth {
    public static long unixDateTime() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Add one month to the current date
        LocalDate dateAfterOneMonth = currentDate.plusMonths(1);

        // Specify 12:00 (noon) time
        LocalTime noon = LocalTime.NOON;

        // Combine date and time to create ZonedDateTime in the system default time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateAfterOneMonth, noon, ZoneId.systemDefault());

        // Convert to Instant to get Unix time
        Instant instant = zonedDateTime.toInstant();

        // Get Unix time in seconds
        long unixTime = instant.getEpochSecond();

        // Print the Unix time
        System.out.println("Unix time after one month at 12 o'clock: " + unixTime);
		return unixTime;
    }

   
}
