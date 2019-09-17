package com.ledzion.bookingservice.controller;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.Customer;
import com.ledzion.bookingservice.service.BicycleService;
import com.ledzion.bookingservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BicycleService bicycleService;

    @GetMapping(value = "customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id ) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping(value = "bicycles")
    public ResponseEntity<List<Bicycle>> getBicycleByTypeSize(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "size", required = false) String size
            ) {
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize(type, size);
        return ResponseEntity.status(HttpStatus.OK).body(bicycles);
    }
}
