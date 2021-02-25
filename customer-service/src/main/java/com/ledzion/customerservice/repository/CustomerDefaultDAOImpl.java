package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Address;
import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Repository
public class CustomerDefaultDAOImpl implements CustomerDAO {

    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer("1", "Jan Kowalski", "+48 234234234", "kowaslki@abc.com",
                    new Address("City", "PL", "00-000", "Street", "12")),
            new Customer("2", "Anna Kowalska", "+48 234234567", "kowaslka@abc.com",
                    new Address("City", "PL", "00-000", "Street", "12")),
            new Customer("3", "Anna Kowalska", "+48 234234567", "kowaslka@abc.com",
                    new Address("Test", "PL", "00-000", "Street", "12")),
            new Customer("4", "Anna Kowalska", "+48 234234567", "kowaslka@abc.com",
                    new Address("City", "PL", "00-000", "Street", "12"))
    ));

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return getAllCustomers().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public boolean addBooking(Customer updatedCustomer) {
        Customer customer = getCustomerById(updatedCustomer.getId()).get();
        return customers.remove(customer) && customers.add(updatedCustomer);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return customers.add(customer);
    }
}