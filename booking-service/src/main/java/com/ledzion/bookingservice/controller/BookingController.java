package com.ledzion.bookingservice.controller;

import com.ledzion.bookingservice.model.BookingRequest;
import com.ledzion.bookingservice.service.BookingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private static final String BICYCLE_BOOKED = "Bicycle booked.";

    private static final String BICYCLE_AVAILABLE = "Bicycle available.";

    private static final String BICYCLE_UNAVAILABLE = "Bicycle unavailable.";

    private static final String ERROR_WHILE_BOOKING_BICYCLE = "Error while booking bicycle. Provided data incorrect.";

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    private static final String BOOKING_DETAILS_MISSING = "Booking details missing.";

    @Autowired
    private BookingService bookingService;

    //TODO: add possibility that size or type is empty
    @PutMapping
    public ResponseEntity<String> bookBicycle(@RequestBody BookingRequest bookingRequest) {
        LOGGER.debug("Booking bicycles of type {} and sie {} for customer with id {} for period: start date = {},"
                + " end date = {}.", bookingRequest.getType(), bookingRequest.getSize(),
                bookingRequest.getUserId(), bookingRequest.getStartDate(),
                bookingRequest.getEndDate());

        if(bookingRequest.getStartDate() == null || bookingRequest.getEndDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BOOKING_DETAILS_MISSING);
        }
        if(bookingRequest.getEndDate().isBefore(bookingRequest.getStartDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(END_DATE_IS_AFTER_START_DATE);
        }

        return bookingService.bookBicycle(bookingRequest)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.OK).body(ERROR_WHILE_BOOKING_BICYCLE);
    }
}
