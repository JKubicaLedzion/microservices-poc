package com.ledzion.bookingservice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Customer {

    private long id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private Map<Long, List<BookingPeriod>> bookings;

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<Long,List<BookingPeriod>> getBookings() {
        return bookings;
    }

    public void setBookings( Map<Long, List<BookingPeriod>> bookings ) {
        this.bookings = bookings;
    }

    @Override public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals( name, customer.name ) && Objects.equals( phone, customer.phone )
                && Objects.equals( email, customer.email ) && Objects.equals( address, customer.address ) && Objects
                .equals( bookings, customer.bookings );
    }

    @Override public int hashCode() {
        return Objects.hash( id, name, phone, email, address, bookings );
    }

    @Override public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", name='" + name +
                ", phone='" + phone +
                ", email='" + email +
                ", address=" + address +
                ", bookings=" + bookings +
                '}';
    }
}
