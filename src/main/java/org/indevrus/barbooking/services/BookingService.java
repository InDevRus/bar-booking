package org.indevrus.barbooking.services;

import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.models.BookingTimeSpan;

import java.util.*;

public interface BookingService {
    Map<Calendar, List<BookingTimeSpan>> findFreePlaces(int tableNumber);

    Optional<Booking> getByTime(int tableNumber, Calendar date, BookingTimeSpan timeSpan);

    Collection<Booking> submit(Collection<Booking> bookings);
}
