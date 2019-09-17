package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Customer;

import java.time.LocalDate;
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

    @Override
    public boolean addBooking(long userId, long bicycleId, LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
