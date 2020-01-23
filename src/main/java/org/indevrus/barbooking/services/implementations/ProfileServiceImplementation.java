package org.indevrus.barbooking.services.implementations;

import org.indevrus.barbooking.dao.BookingDAO;
import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class ProfileServiceImplementation implements ProfileService {
    private BookingDAO bookingDAO;

    @Override
    public Collection<Booking> findAll(String phoneNumber, String name) {
        return bookingDAO.findByRequisites(phoneNumber, name);
    }

    @Autowired
    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
}
