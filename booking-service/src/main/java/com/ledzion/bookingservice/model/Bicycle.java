package com.ledzion.bookingservice.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bicycle {

    private long id;
    private Type type;
    private Size size;
    private Map<Long, BookingPeriod> bookings;

    public Bicycle() {
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

    public Map<Long, BookingPeriod> getBookings() {
        return bookings;
    }

    public void setBookings(Map<Long, BookingPeriod> bookings ) {
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
