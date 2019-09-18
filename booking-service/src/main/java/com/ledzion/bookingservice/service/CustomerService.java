package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;

    public Customer getCustomerById(long id) {
        return restTemplate.getForObject( "http://customer-service/customers/" + id, Customer.class );
    }
}
