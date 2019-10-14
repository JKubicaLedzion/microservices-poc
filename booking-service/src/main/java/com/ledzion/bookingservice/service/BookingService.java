package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingPeriod;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BicycleService bicycleService;

    public boolean bookBicycle(long userId, String type, String size, LocalDate startDate, LocalDate endDate) {
        // get user and check if exists. if not - throw error
        Customer customer = getCustomerById(userId);

        // check if bicycle exists for given type, size, if not - throw error
        Bicycle bicycle = getCustomerById(type, size);

        // if exists - check dates if can be booked, if not - throw error
        // of add new endpoint to bicycle-service which checks bicycle availability

        List<BookingPeriod> bookings = bicycle.getBookings().values().stream()
                .filter(b -> b.containsDate(startDate) || b.containsDate(endDate))
                .collect( Collectors.toList());
        if(!bookings.isEmpty()) {
            return false;
        }


        // call addBooking in customer-service passing userId, bicycleId, dates
        // call bookBicycle in bicycle-service passing userId, bicycleId, dates
        return false;
    }

    private Customer getCustomerById(long userId) {
        // where error handling? here or CustomerService class
        return customerService.getCustomerById(userId);
    }

    private Bicycle getCustomerById(String type, String size) {
        return bicycleService.getBicyclesByTypeSize(type, size).get(0);
    }
}
