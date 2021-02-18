package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BicycleService bicycleService;

    public boolean bookBicycle(long userId, String type, String size, LocalDate startDate, LocalDate endDate) {
        // get user and check if exists. if not - throw error
        Customer customer = getCustomerById(userId);
        //TODO: add error

        // check if bicycle exists for given type, size, if not - throw error
        Bicycle bicycle = getBicycleByTypeSize(type, size);
        //TODO: add error

        // if exists - check bicycle availability
        if(!bicycleAvailable(bicycle.getId(), startDate, endDate)) {
            return false;
        }
        
        // call addBooking in customer-service passing userId, bicycleId, dates
        // call bookBicycle in bicycle-service passing userId, bicycleId, dates
        return false;
    }

    private Customer getCustomerById(long userId) {
        return customerService.getCustomerById(userId);
    }

    private Bicycle getBicycleByTypeSize(String type, String size) {
        return bicycleService.getBicyclesByTypeSize(type, size).get(0);
    }

    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return bicycleService.bicycleAvailable(id, startDate, endDate);
    }
}
