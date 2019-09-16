package com.ledzion.customerservice.controller;

import com.ledzion.customerservice.model.Customer;
import com.ledzion.customerservice.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private static final String CUSTOMER_NOT_FOUND = "Customer not found.";

    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE =
            "No Response From Customer Service at this moment. " + " Service will be back shortly.";

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @HystrixCommand(fallbackMethod = "getCustomerByIdFallback")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCustomerById(@PathVariable("id") long id) {
        LOGGER.debug("Getting customer with id {}.", id);
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(customer.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(CUSTOMER_NOT_FOUND);
    }

    @HystrixCommand(fallbackMethod = "getAllCustomersFallback")
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllCustomers() {
        LOGGER.debug("Getting all customers.");
        List<Customer> customers = customerService.getAllCustomers();
        return customers.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(CUSTOMER_NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getCustomerByIdFallback(@PathVariable("id") long id) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity getAllCustomersFallback() {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }
}
