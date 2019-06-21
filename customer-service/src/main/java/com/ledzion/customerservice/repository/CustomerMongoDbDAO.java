package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public class CustomerMongoDbDAO implements CustomerDAO {

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }
}
