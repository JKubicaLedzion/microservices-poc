package com.ledzion.customerservice.service;

import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getCustomerById(long id);

    List<Customer> getAllCustomers();

    boolean addBooking(BookingParameters bookingParameters);
}
