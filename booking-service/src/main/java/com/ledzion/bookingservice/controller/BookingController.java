package com.ledzion.bookingservice.controller;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.Customer;
import com.ledzion.bookingservice.service.BicycleService;
import com.ledzion.bookingservice.service.BookingService;
import com.ledzion.bookingservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private static final String BICYCLE_BOOKED = "Bicycle booked.";

    private static final String ERROR_WHILE_BOOKING_BICYCLE = "Error while booking bicycle. Provided data incorrect.";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private BicycleService bicycleService;

    //todo: test method, to be deleted
    @GetMapping(value = "customers/{id}")
//    public ResponseEntity<Integer> getCustomerById(@PathVariable("id") long id ) {
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id ) {
        List<ServiceInstance> localInstance = client.getInstances("customer-service");
        Customer customer = customerService.getCustomerById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(localInstance.get(0).getPort());
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    //todo: test method, to be deleted
    @GetMapping(value = "bicycles")
    public ResponseEntity<List<Bicycle>> getBicycleByTypeSize(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "size", required = false) String size
            ) {
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize(type, size);
        return ResponseEntity.status(HttpStatus.OK).body(bicycles);
    }

    @PostMapping
    public ResponseEntity<String> bookBicycle(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {
        LOGGER.debug("Booking bicycles of type {} and sie {} for customer with id {} for period: start date = {},"
                + " end date = {}.", type, size, userId, startDate, endDate);
        return bookingService.bookBicycle(userId, type, size, startDate, endDate)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.OK).body(ERROR_WHILE_BOOKING_BICYCLE);
    }
}
