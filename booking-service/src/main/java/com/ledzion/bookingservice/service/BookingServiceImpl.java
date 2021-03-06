package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.exception.BadRequest;
import com.ledzion.bookingservice.exception.ServiceException;
import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.BookingRequest;
import com.ledzion.bookingservice.model.Customer;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private BicycleService bicycleService;

    private Customer getCustomer(BookingRequest bookingRequest) {
        try {
            return customerServiceProxy.getCustomerById(bookingRequest.getUserId());
        } catch (final FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new BadRequest(e.getMessage());
            }
            throw new ServiceException(e.getMessage());
        }
    }

    private void addBookingForCustomer(BookingParameters bookingParameters) {
        try {
            customerServiceProxy.addBooking(bookingParameters);
        } catch (final FeignException e) {
            if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                throw new BadRequest(e.getMessage());
            }
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean bookBicycle(BookingRequest bookingRequest) {
        validateBookingDates(bookingRequest);

        // get user and check if exists
        Customer customer = getCustomer(bookingRequest);

        // check if bicycle exists for given type, size
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize(bookingRequest.getType(), bookingRequest.getSize());

        // if exists - check bicycle availability
        Bicycle availableBicycle = getAvailableBicycle(bicycles, bookingRequest);

        // Required: add solution for rollback in case bicycle-service throws exception
        try {
            // add booking for customer
            addBookingForCustomer(prepareBookingParameters(bookingRequest, availableBicycle.getId()));
            // add booking for bicycle
            bicycleService.addBooking(prepareBookingParameters(bookingRequest, availableBicycle.getId()));
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    private BookingParameters prepareBookingParameters(BookingRequest bookingRequest, String bicycleId) {
        return BookingParameters.builder()
                .userId(bookingRequest.getUserId())
                .bicycleId(bicycleId)
                .startDate(bookingRequest.getStartDate())
                .endDate(bookingRequest.getEndDate())
                .build();
    }

    private void validateBookingDates(BookingRequest bookingRequest) {
        if (bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) {
            throw new BadRequest(END_DATE_IS_AFTER_START_DATE);
        }
    }

    private Bicycle getAvailableBicycle(List<Bicycle> bicycles, BookingRequest bookingRequest) {
        return bicycles.stream().filter(bicycle -> {
            try {
                bicycleService.bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate());
                return true;
            } catch (BadRequest e) {
            }
            return false;
        }).findFirst().orElseThrow(() -> new BadRequest("No available bicycles."));
    }
}
