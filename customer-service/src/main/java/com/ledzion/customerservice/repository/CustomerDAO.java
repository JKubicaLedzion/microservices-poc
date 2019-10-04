package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> getCustomerById(long id);

    List<Customer> getAllCustomers();

    boolean addBooking(long id, long bicycleId, LocalDate startDate, LocalDate endDate);
}
