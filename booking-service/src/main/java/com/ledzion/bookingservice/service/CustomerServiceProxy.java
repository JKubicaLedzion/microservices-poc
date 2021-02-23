package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.BookingParameters;
import com.ledzion.bookingservice.model.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="customer-service")
public interface CustomerServiceProxy {

    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable(name = "id") long id);

    @PutMapping("/customers/booking")
    void addBooking(@RequestBody BookingParameters bookingParameters);
}
