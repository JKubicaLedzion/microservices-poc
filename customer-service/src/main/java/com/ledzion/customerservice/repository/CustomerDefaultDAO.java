package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Address;
import com.ledzion.customerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDefaultDAO implements CustomerDAO {

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.of(new Customer(1, "Jan Kowalski", "+48 234234234", "kowaslki@abc.com",
                new Address("City", "PL", "00-000", "Street", "12")));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(Arrays.asList(
                new Customer(1, "Jan Kowalski", "+48 234234234", "kowaslki@abc.com",
                        new Address("City", "PL", "00-000", "Street", "12")),
                new Customer(2, "Anna Kowalska", "+48 234234567", "kowaslka@abc.com",
                        new Address("City", "PL", "00-000", "Street", "12"))
        ));
    }
}
