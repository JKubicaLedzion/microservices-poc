package com.ledzion.bicycleservice.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bicycle {

    private long id;
    private Type type;
    private Size size;
    private Map<String, BookingPeriod> bookings;
    //rebase test

    public Bicycle(long id, Type type, Size size) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.bookings = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Map<String, BookingPeriod> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, BookingPeriod> bookings ) {
        this.bookings = bookings;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bicycle bicycle = (Bicycle) o;
        return id == bicycle.id &&
                type == bicycle.type &&
                size == bicycle.size &&
                Objects.equals( bookings, bicycle.bookings );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, size, bookings );
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "id=" + id +
                ", typeDescription=" + type +
                ", size=" + size +
                ", bookings=" + bookings +
                '}';
    }
}
