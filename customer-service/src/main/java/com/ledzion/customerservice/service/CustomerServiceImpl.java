package com.ledzion.customerservice.service;

import com.ledzion.customerservice.exceptions.BadRequest;
import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;
import com.ledzion.customerservice.repository.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    private static final String CUSTOMER_WITH_PROVIDED_ID_DOESN_T_EXISTS = "Customer with provided Id doesn't exists.";

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public boolean addBooking(BookingParameters bookingParameters) {
        if (!getCustomerById(bookingParameters.getUserId()).isPresent()) {
            throw new BadRequest(CUSTOMER_WITH_PROVIDED_ID_DOESN_T_EXISTS);
        }

        BookingPeriod bookingPeriod = new BookingPeriod(bookingParameters.getStartDate(), bookingParameters.getEndDate());
        // separate method
        Customer customer = getCustomerById(bookingParameters.getUserId()).get();
        Map<String, List<BookingPeriod>> customerBookings = customer.getBookings();
        if (customerBookings == null || customerBookings.isEmpty()) {
            customer.setBookings(new HashMap<>());
        }

        // separate method
        List<BookingPeriod> bicycleBookings = customer.getBookings().get(bookingParameters.getBicycleId());
        if (bicycleBookings == null || bicycleBookings.isEmpty()) {
            customer.getBookings().put(bookingParameters.getBicycleId(), new ArrayList<>(Arrays.asList(bookingPeriod)));
        } else {
            validateBookingParameters(bookingParameters, bicycleBookings);
            bicycleBookings.add(bookingPeriod);
            customer.getBookings().put(bookingParameters.getBicycleId(), bicycleBookings);
        }

        return customerDAO.addBooking(customer);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    private void validateBookingParameters(BookingParameters bookingParameters, List<BookingPeriod> bicycleBookings) {
        if (bookingParameters.getStartDate().isAfter(bookingParameters.getEndDate())) {
            throw new BadRequest(END_DATE_IS_AFTER_START_DATE);
        }
        ;
        if (bicycleBookings.stream()
                .filter(b -> b.containsDate(bookingParameters.getStartDate()) || b.containsDate(bookingParameters.getEndDate()))
                .count() != 0) {
            throw new BadRequest("Booking of bicycle exists for provided date range.");
        }
        ;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
