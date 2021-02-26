package com.ledzion.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    @NotBlank(message = "User Id should be provided.")
    private String id;

    @NotBlank(message = "User name should be provided.")
    private String name;

    @NotBlank(message = "User phone should be provided.")
    private String phone;

    private String email;

    private Address address;

    private Map<String, List<BookingPeriod>> bookings;

    public Customer(String id, String name, String phone, String email, Address address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        bookings = new HashMap<>();
    }
}
