package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private static final String CUSTOMER_SERVICE_URL = "http://customer-service/customers/";

    @Autowired
    private RestTemplate restTemplate;

    public Customer getCustomerById(long id) {
        return restTemplate.getForObject( CUSTOMER_SERVICE_URL + id, Customer.class );
    }

    public void addBooking(BookingParameters bookingParameters) {
        restTemplate.put( CUSTOMER_SERVICE_URL + "booking", bookingParameters);
    }
}
