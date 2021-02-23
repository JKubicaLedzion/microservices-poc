package com.ledzion.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private long id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private Map<Long, List<BookingPeriod>> bookings;

    public Customer(long id, String name, String phone, String email, Address address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        bookings = new HashMap<>();
    }
}
