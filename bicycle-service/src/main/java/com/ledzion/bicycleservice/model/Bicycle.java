package com.ledzion.bicycleservice.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Bicycle {

    private long id;
    private Type type;
    private Size size;
    private Map<Long, List<BookingPeriod>> bookings;

    public Bicycle(long id, Type type, Size size) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.bookings = new HashMap<>();
    }
}
