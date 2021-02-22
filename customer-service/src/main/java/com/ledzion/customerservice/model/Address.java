package com.ledzion.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Address {

    private String city;
    private String country;
    private String postalCode;
    private String street;
    private String number;
}
