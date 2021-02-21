package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.BookingRequest;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BicycleService bicycleService;

    public boolean bookBicycle(BookingRequest bookingRequest) {
        // get user and check if exists. if not - throw error
        Customer customer = customerService.getCustomerById(bookingRequest.getUserId());
        //TODO: add error

        // check if bicycle exists for given type, size, if not - throw error
        Bicycle bicycle = bicycleService.getBicyclesByTypeSize(bookingRequest.getType(), bookingRequest.getSize()).get(0);
        //TODO: add error

        // if exists - check bicycle availability
        if(!bicycleService.bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            return false;
        }

        // add booking for customer
        customerService.addBooking(prepareBookingParameters(bookingRequest, bicycle.getId()));

        // add booking for bicycle
        bicycleService.addBooking(prepareBookingParameters(bookingRequest, bicycle.getId()));
        return true;
    }

    private BookingParameters prepareBookingParameters(BookingRequest bookingRequest, long bicycleId){
        return BookingParameters.builder()
                .userId(bookingRequest.getUserId())
                .bicycleId(bicycleId)
                .startDate(bookingRequest.getStartDate())
                .endDate(bookingRequest.getEndDate())
                .build();
    }
}
