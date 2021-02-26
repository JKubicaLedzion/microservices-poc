package com.ledzion.customerservice.service;

import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getCustomerById(String id);

    List<Customer> getAllCustomers();

    boolean addBooking(BookingParameters bookingParameters);

    boolean addCustomer(Customer customer);
}
