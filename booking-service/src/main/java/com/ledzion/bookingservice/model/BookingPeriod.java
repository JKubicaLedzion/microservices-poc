package com.ledzion.bookingservice.model;

import java.time.LocalDate;
import java.util.Objects;

public class BookingPeriod {

    private LocalDate startDate;
    private LocalDate endDate;

    public BookingPeriod() {
    }

    public BookingPeriod( LocalDate startDate, LocalDate endDate ) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate( LocalDate startDate ) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate( LocalDate endDate ) {
        this.endDate = endDate;
    }

    public boolean containsDate(LocalDate date) {
        return date.isEqual(startDate)
                || date.isEqual(endDate)
                || (date.isAfter(startDate) && date.isBefore(endDate));
    }

    @Override public boolean equals( Object o ) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookingPeriod that = (BookingPeriod) o;
        return Objects.equals( startDate, that.startDate )
                && Objects.equals( endDate, that.endDate );
    }

    @Override public int hashCode() {
        return Objects.hash( startDate, endDate );
    }

    @Override public String toString() {
        return "BookingPeriod{" +
                "startDate=" + startDate +
                ", endDate=" + endDate + '}';
    }
}
