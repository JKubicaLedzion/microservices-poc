package com.ledzion.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    public long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(long bicycleId) {
        this.bicycleId = bicycleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingParameters that = (BookingParameters) o;
        return userId == that.userId && bicycleId == that.bicycleId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bicycleId, startDate, endDate);
    }

    @Override
    public String toString() {
        return "BookingParametersCustomerService{" +
                "userId=" + userId +
                ", bicycleId=" + bicycleId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
