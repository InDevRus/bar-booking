package org.indevrus.barbooking.models;

import java.util.Arrays;

public enum BookingTimeSpan {
    _9("9:00"),
    _10("10:00"),
    _11("11:00"),
    _12("12:00"),
    _13("13:00"),
    _14("14:00"),
    _15("15:00"),
    _16("16:00"),
    _17("17:00"),
    _18("18:00"),
    _19("19:00"),
    _20("20:00"),
    _21("21:00");

    private final String start;

    BookingTimeSpan(String start) {
        this.start = start;
    }

    public static BookingTimeSpan fromTime(String time) {
        return Arrays.stream(BookingTimeSpan.values())
                .filter(timespan -> timespan.getStart().equals(time))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return start;
    }

    public String getStart() {
        return start;
    }
}
