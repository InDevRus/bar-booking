package org.indevrus.barbooking.controllers;

import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.models.BookingTimeSpan;
import org.indevrus.barbooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking/")
public class BookingController {
    private BookingService bookingService;

    @GetMapping("/")
    public String booking() {
        return "booking/index";
    }

    @GetMapping("/table")
    public String table(@RequestParam("tableNumber") int tableNumber, Model model) {
        model.addAttribute("tableNumber", tableNumber);

        var freePlaces = bookingService.findFreePlaces(tableNumber);
        model.addAttribute("freePlaces", freePlaces);
        model.addAttribute("dates", freePlaces
                .keySet()
                .stream()
                .filter(calendar -> !freePlaces.get(calendar).isEmpty())
                .sorted()
                .collect(Collectors.toList()));
        model.addAttribute("dayMonthFormatter", new SimpleDateFormat("dd.MM"));
        model.addAttribute("dateFormatter", new SimpleDateFormat("dd.MM.yyyy"));

        return "booking/table";
    }

    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String submit(@RequestParam("date") String dateString,
                         @RequestParam("tableNumber") int tableNumber,
                         @RequestParam(value = "timespans", required = false) List<String> timespans,
                         @RequestParam("name") String name,
                         @RequestParam("phone") String phone,
                         Model model) throws ParseException {
        var errors = new ArrayList<String>();

        var calendar = Calendar.getInstance();
        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        calendar.setTime(dateFormatter.parse(dateString));

        List<Booking> bookings = null;

        if (Objects.isNull(timespans) || timespans.isEmpty()) {
            errors.add("Вы не выбрали ни одного из свободных мест.");
        } else {
            bookings = timespans.stream()
                    .map(BookingTimeSpan::fromTime)
                    .map(timeSpan -> {
                        var booking = new Booking();
                        booking.setDate(calendar);
                        booking.setName(name.toUpperCase());
                        booking.setPhoneNumber(phone);
                        booking.setTableNumber(tableNumber);
                        booking.setTimespan(timeSpan);
                        return booking;
                    })
                    .collect(Collectors.toList());

            bookings.forEach(booking -> bookingService.getByTime(tableNumber, calendar, booking.getTimespan())
                    .ifPresent(found -> errors.add(MessageFormat.format(
                            "Время {0} уже забронировано. Выберите другое.", booking.getTimespan()))));
        }
        if (!errors.isEmpty()) {
            model.addAttribute("tableNumber", tableNumber);
            model.addAttribute("errors", errors);
            return "booking/errors";
        }

        bookingService.submit(bookings);

        return MessageFormat.format(
                "redirect:/profile/show?phone={0}&name={1}", phone,
                URLEncoder.encode(name, StandardCharsets.UTF_8));
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
