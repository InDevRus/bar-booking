package org.indevrus.barbooking.dao;

import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.models.BookingTimeSpan;

import java.util.*;


public interface BookingDAO {
    void persistAll(Collection<Booking> bookings);

    Optional<Booking> getByTime(int tableNumber, Calendar calendar, BookingTimeSpan timeSpan);

    Collection<Booking> findByRequisites(String phoneNumber, String name);

    Map<Calendar, List<BookingTimeSpan>> getAllBookings(int tableNumber, Calendar start, Calendar end);
}
