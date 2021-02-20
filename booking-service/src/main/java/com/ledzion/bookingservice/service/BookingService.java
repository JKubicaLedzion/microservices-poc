package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.BookingRequest;
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

    public boolean bookBicycle(BookingRequest bookingRequest) {
        // get user and check if exists. if not - throw error
        Customer customer = getCustomerById(bookingRequest.getUserId());
        //TODO: add error

        // check if bicycle exists for given type, size, if not - throw error
        Bicycle bicycle = getBicycleByTypeSize(bookingRequest.getType(), bookingRequest.getSize());
        //TODO: add error

        // if exists - check bicycle availability
        if(!bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            return false;
        }
        
        // add booking for customer
        addBookingForCustomer(bookingRequest, bicycle.getId());

        // add booking for bicycle
        addBookingForBicycle(bookingRequest, bicycle.getId());
        return true;
    }

    private void addBookingForBicycle(BookingRequest bookingRequest, long bicycleId) {
        bicycleService.addBooking(prepareBookingParameters(bookingRequest, bicycleId));
    }

    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return bicycleService.bicycleAvailable(id, startDate, endDate);
    }

    private Customer getCustomerById(long userId) {
        return customerService.getCustomerById(userId);
    }

    private Bicycle getBicycleByTypeSize(String type, String size) {
        return bicycleService.getBicyclesByTypeSize(type, size).get(0);
    }

    private void addBookingForCustomer(BookingRequest bookingRequest, long bicycleId){
        customerService.addBooking(prepareBookingParameters(bookingRequest, bicycleId));
    }

    // TODO: add Lombok Builder annotation
    private BookingParameters prepareBookingParameters(BookingRequest bookingRequest, long bicycleId){
        BookingParameters bookingParameters = new BookingParameters();
        bookingParameters.setUserId(bookingRequest.getUserId());
        bookingParameters.setBicycleId(bicycleId);
        bookingParameters.setStartDate(bookingRequest.getStartDate());
        bookingParameters.setEndDate(bookingRequest.getEndDate());
        return bookingParameters;
    }
}
