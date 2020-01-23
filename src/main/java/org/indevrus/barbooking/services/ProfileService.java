package org.indevrus.barbooking.services;

import org.indevrus.barbooking.models.Booking;

import java.util.Collection;

public interface ProfileService {
    Collection<Booking> findAll(String phoneNumber, String name);
}
