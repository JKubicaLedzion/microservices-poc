package com.ledzion.bicycleservice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bicycle {

    private long id;
    private Type type;
    private Size size;
    private List<LocalDate> rentalDates;

    public Bicycle(long id, Type type, Size size) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.rentalDates = new ArrayList<>();
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

    public List<LocalDate> getRentalDates() {
        return rentalDates;
    }

    public void setRentalDates(List<LocalDate> rentalDates) {
        this.rentalDates = rentalDates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bicycle bicycle = (Bicycle) o;
        return id == bicycle.id &&
                type == bicycle.type &&
                size == bicycle.size &&
                Objects.equals(rentalDates, bicycle.rentalDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, size, rentalDates);
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "id=" + id +
                ", typeDescription=" + type +
                ", size=" + size +
                ", rentalDates=" + rentalDates +
                '}';
    }
}
