package com.ledzion.bookingservice.model;

import java.util.Objects;

public class Address {

    private String city;
    private String country;
    private String postalCode;
    private String street;
    private String number;

    public Address() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(country, address.country) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, postalCode, street, number);
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
