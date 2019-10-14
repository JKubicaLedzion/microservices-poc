package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Address;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDefaultDAO implements CustomerDAO {

    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1, "Jan Kowalski", "+48 234234234", "kowaslki@abc.com",
                    new Address("City", "PL", "00-000", "Street", "12")),
            new Customer(2, "Anna Kowalska", "+48 234234567", "kowaslka@abc.com",
                    new Address("City", "PL", "00-000", "Street", "12"))
    ));

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return getAllCustomers().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public boolean addBooking(long id, long bicycleId, LocalDate startDate, LocalDate endDate) {
        Optional<Customer> customer = getCustomerById(id);
        if(!customer.isPresent()) {
            return false;
        }
        customer.get().getBookings().put(bicycleId, new BookingPeriod(startDate, endDate));
        return true;
    }
}
