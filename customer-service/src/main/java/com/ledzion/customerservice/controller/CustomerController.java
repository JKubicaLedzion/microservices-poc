package com.ledzion.customerservice.controller;

import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.Customer;
import com.ledzion.customerservice.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private static final String BOOKING_ADDED = "Bicycle booking added.";

    private static final String CUSTOMER_NOT_FOUND = "Customer not found.";

    private static final String ERROR_WHILE_ADDING_BOOKING = "Error while adding bicycle booking. Provided data incorrect.";

    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE =
            "No Response From Customer Service at this moment. " + " Service will be back shortly.";

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    private static final String BOOKING_DETAILS_MISSING = "Booking details missing.";

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @HystrixCommand(fallbackMethod = "getCustomerByIdFallback")
    @GetMapping(value = "/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") long id) {
        LOGGER.debug("Getting customer with id {}.", id);
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(customer.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(CUSTOMER_NOT_FOUND);
    }

    @HystrixCommand(fallbackMethod = "getAllCustomersFallback")
    @GetMapping
    public ResponseEntity getAllCustomers() {
        LOGGER.debug("Getting all customers.");
        List<Customer> customers = customerService.getAllCustomers();
        return customers.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(CUSTOMER_NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @HystrixCommand(fallbackMethod = "addBookingFallback")
    @PutMapping("/booking")
    public ResponseEntity addBooking(@RequestBody BookingParameters bookingParameters) {
        LOGGER.debug("Adding bicycle booking with start date {} and end date {} for customer with Id {}.",
                bookingParameters.getStartDate(),
                bookingParameters.getEndDate(), bookingParameters.getUserId());
        if(bookingParameters.getStartDate() == null || bookingParameters.getEndDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BOOKING_DETAILS_MISSING);
        }
        if(bookingParameters.getEndDate().isBefore(bookingParameters.getStartDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(END_DATE_IS_AFTER_START_DATE);
        }
        return customerService.addBooking(bookingParameters)
                ? ResponseEntity.status(HttpStatus.OK).body(BOOKING_ADDED)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_WHILE_ADDING_BOOKING);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getCustomerByIdFallback(@PathVariable("id") long id) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity getAllCustomersFallback() {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity addBookingFallback(BookingParameters bookingParameters) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }
}
