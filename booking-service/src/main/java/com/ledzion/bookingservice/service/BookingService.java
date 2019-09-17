package com.ledzion.bookingservice.service;

import java.time.LocalDate;

public class BookingService {

    public boolean bookBicycle(long userId, String type, String size, LocalDate startDate, LocalDate endDate) {
        // get user and check if exists. if not - throw error
        // check if bicycle exists for given type, size, if not - throw error
        // if exists - check dates if can be booked, if not - throw error

        // call addBooking in customer-service passing userId, bicycleId, dates
        // call bookBicycle in bicycle-service passing userId, bicycleId, dates
        return false;
    }
}
