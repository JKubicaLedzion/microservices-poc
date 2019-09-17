package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    public Customer getCustomerById(long id) {
        Customer customer = new RestTemplate().getForObject( "http://localhost:8082/customers/" + id, Customer.class );
        return customer;
    }
}
