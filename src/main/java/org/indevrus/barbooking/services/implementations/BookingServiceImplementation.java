package org.indevrus.barbooking.services.implementations;

import org.indevrus.barbooking.dao.BookingDAO;
import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.models.BookingTimeSpan;
import org.indevrus.barbooking.services.BookingService;
import org.indevrus.barbooking.utilities.BookingTimeUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class BookingServiceImplementation implements BookingService {
    private BookingDAO bookingDAO;

    @Override
    public Map<Calendar, List<BookingTimeSpan>> findFreePlaces(int tableNumber) {
        var start = BookingTimeUtilities.addDays(BookingTimeUtilities.getCurrentDate());
        var end = BookingTimeUtilities.addDays(start, BookingTimeUtilities.DEFAULT_DAYS_COUNT);

        var occupied = bookingDAO.getAllBookings(tableNumber, start, end);

        Function<Calendar, List<BookingTimeSpan>> findFreePlaces = calendar -> {
            var free = new ArrayList<>(Arrays.asList(BookingTimeSpan.values()));
            if (occupied.containsKey(calendar))
                free.removeAll(occupied.get(calendar));
            return free;
        };

        var result = Stream.iterate(start, current -> current.compareTo(end) < 0, BookingTimeUtilities::addDays)
                .collect(Collectors.toMap(calendar -> calendar, findFreePlaces));

        result.values().forEach(timeSpans -> timeSpans.removeIf(timespan -> timeSpans
                .stream()
                .noneMatch(anotherTimespan -> Math.abs(anotherTimespan.ordinal() - timespan.ordinal()) == 1)));
        return result;
    }

    @Override
    public Optional<Booking> getByTime(int tableNumber, Calendar date, BookingTimeSpan timeSpan) {
        return bookingDAO.getByTime(tableNumber, date, timeSpan);
    }

    @Override
    public Collection<Booking> submit(Collection<Booking> bookings) {
        bookingDAO.persistAll(bookings);
        return bookings;
    }

    @Autowired
    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
}
