package com.ledzion.bicycleservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingPeriod {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public boolean containsDate(LocalDate date) {
        return date.isEqual(startDate)
                || date.isEqual(endDate)
                || (date.isAfter(startDate) && date.isBefore(endDate));
    }
}
