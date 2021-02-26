package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.exception.BadRequest;
import com.ledzion.bookingservice.exception.ServiceException;
import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.BookingRequest;
import com.ledzion.bookingservice.model.Customer;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private BicycleService bicycleService;

    public boolean bookBicycle(BookingRequest bookingRequest) {
        validateBookingDates(bookingRequest);

        // get user and check if exists
        Customer customer = getCustomer(bookingRequest);
        if(customer == null) {
            return false;
        }

        // check if bicycle exists for given type, size
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize(bookingRequest.getType(), bookingRequest.getSize());
        if(bicycles == null || bicycles.isEmpty()) {
            return false;
        }

        // if exists - check bicycle availability


        Optional<Bicycle> availableBicycle = bicycles.stream().filter(bicycle -> {
            try {
                bicycleService.bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate());
                return true;
            } catch (BadRequest e) {
            }
            return false;
        }).findFirst();

        if(!availableBicycle.isPresent()) {
            return false;
        }

//        Bicycle availableBicycle = new Bicycle();
//        for (Bicycle bicycle : bicycles) {
//            try{
//                bicycleService.bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate());
//                availableBicycle = bicycle;
//                break;
//            } catch (BadRequest e) {
//            }
//        }

        // add booking for customer
        addBookingForCustomer(prepareBookingParameters(bookingRequest, availableBicycle.get().getId()));

        // add booking for bicycle
        bicycleService.addBooking(prepareBookingParameters(bookingRequest, availableBicycle.get().getId()));
        return true;
    }

    private Customer getCustomer(BookingRequest bookingRequest) {
        Customer customer = null;
        try {
            customer = customerServiceProxy.getCustomerById(bookingRequest.getUserId());
        } catch (final FeignException e) {
            if (e.status() == 404) {
                throw new BadRequest(e.getMessage());
            }
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return customer;
    }

    private void addBookingForCustomer(BookingParameters bookingParameters) {
        try {
            customerServiceProxy.addBooking(bookingParameters);
        } catch (final FeignException e) {
            if (e.status() == 400) {
                throw new BadRequest(e.getMessage());
            }
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private BookingParameters prepareBookingParameters(BookingRequest bookingRequest, String bicycleId){
        return BookingParameters.builder()
                .userId(bookingRequest.getUserId())
                .bicycleId(bicycleId)
                .startDate(bookingRequest.getStartDate())
                .endDate(bookingRequest.getEndDate())
                .build();
    }

    private void validateBookingDates(BookingRequest bookingRequest) {
        if(bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) {
            throw new BadRequest(END_DATE_IS_AFTER_START_DATE);
        }
    }
}
