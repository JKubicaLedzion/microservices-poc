package com.ledzion.bookingservice.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Customer {

    private String id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private Map<Long, List<BookingPeriod>> bookings;
}
