package com.ledzion.customerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class BookingParameters {

    private long userId;
    private long bicycleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public BookingParameters() {
    }

    public long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingParameters that = (BookingParameters) o;
        return bicycleId == that.bicycleId
                && startDate.equals(that.startDate)
                && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bicycleId, startDate, endDate);
    }

    @Override
    public String toString() {
        return "BookingParameters{" +
                "bicycleId=" + bicycleId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
