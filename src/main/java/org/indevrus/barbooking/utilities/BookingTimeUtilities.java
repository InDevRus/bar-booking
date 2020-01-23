package org.indevrus.barbooking.utilities;

import java.util.Calendar;

public interface BookingTimeUtilities {
    int DEFAULT_DAYS_COUNT = 7;

    static Calendar addDays(Calendar calendar) {
        return addDays(calendar, 1);
    }

    static Calendar addDays(Calendar calendar, int daysCount) {
        var result = Calendar.getInstance();
        result.setTime(calendar.getTime());
        result.add(Calendar.DATE, daysCount);
        return result;
    }

    static Calendar getCurrentDate() {
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
