package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.exception.BadRequest;
import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private static final String CUSTOMER_SERVICE_URL = "http://customer-service/customers/";

    @Autowired
    private RestTemplate restTemplate;

    public Customer getCustomerById(long id) {
        try {
            return restTemplate.getForObject( CUSTOMER_SERVICE_URL + id, Customer.class );
        } catch(final HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BadRequest(e.getResponseBodyAsString());
            }
        }
        return null;
    }

    public void addBooking(BookingParameters bookingParameters) {
        try {
            restTemplate.put( CUSTOMER_SERVICE_URL + "booking", bookingParameters);
        } catch(final HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BadRequest(e.getResponseBodyAsString());
            }
        }
    }
}
