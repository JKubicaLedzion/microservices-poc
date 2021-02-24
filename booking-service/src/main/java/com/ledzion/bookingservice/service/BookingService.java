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

@Service
public class BookingService {

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private BicycleService bicycleService;

    public boolean bookBicycle(BookingRequest bookingRequest) {
        // get user and check if exists
        Customer customer = getCustomer(bookingRequest);

        if(customer == null) {
            return false;
        }

        // check if bicycle exists for given type, size
        Bicycle bicycle = bicycleService.getBicyclesByTypeSize(bookingRequest.getType(), bookingRequest.getSize()).get(0);

        // if exists - check bicycle availability
        if(!bicycleService.bicycleAvailable(bicycle.getId(), bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            return false;
        }

        // add booking for customer
        addBookingForCustomer(prepareBookingParameters(bookingRequest, bicycle.getId()));

        // add booking for bicycle
        bicycleService.addBooking(prepareBookingParameters(bookingRequest, bicycle.getId()));
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

    private BookingParameters prepareBookingParameters(BookingRequest bookingRequest, long bicycleId){
        return BookingParameters.builder()
                .userId(bookingRequest.getUserId())
                .bicycleId(bicycleId)
                .startDate(bookingRequest.getStartDate())
                .endDate(bookingRequest.getEndDate())
                .build();
    }
}
