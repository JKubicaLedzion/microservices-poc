package com.ledzion.bookingservice.controller;

import com.ledzion.bookingservice.model.BookingRequest;
import com.ledzion.bookingservice.service.BookingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private static final String BICYCLE_BOOKED = "Bicycle booked.";
    private static final String ERROR_WHILE_BOOKING_BICYCLE = "Error while booking bicycle. Provided data incorrect.";
    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @PutMapping
    public ResponseEntity<String> bookBicycle(@RequestBody @Valid BookingRequest bookingRequest) {
        LOGGER.debug("Booking bicycles of type {} and sie {} for customer with id {} for period: start date = {},"
                        + " end date = {}.", bookingRequest.getType(), bookingRequest.getSize(),
                bookingRequest.getUserId(), bookingRequest.getStartDate(),
                bookingRequest.getEndDate());
        return bookingServiceImpl.bookBicycle(bookingRequest)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.OK).body(ERROR_WHILE_BOOKING_BICYCLE);
    }
}
