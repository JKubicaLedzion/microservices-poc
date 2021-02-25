package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerMongoDbDAOImpl implements  CustomerDAO{

    @Autowired
    private CustomerMongoDbRepository customerMongoDbRepository;

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return Optional.ofNullable(customerMongoDbRepository.findOne(id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerMongoDbRepository.findAll();
    }

    @Override
    public boolean addBooking(Customer customer) {
        return customerMongoDbRepository.save(customer).equals(customer);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return customerMongoDbRepository.save(customer).equals(customer);
    }
}
