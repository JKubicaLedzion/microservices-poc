package com.ledzion.bookingservice.model;

import lombok.Data;

@Data
public class Address {

    private String city;
    private String country;
    private String postalCode;
    private String street;
    private String number;
}
