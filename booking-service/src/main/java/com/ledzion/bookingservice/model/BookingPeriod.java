package com.ledzion.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Objects;

public class BookingPeriod {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
