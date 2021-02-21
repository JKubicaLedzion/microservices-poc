package com.ledzion.bookingservice.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Bicycle {

    private long id;
    private Type type;
    private Size size;
    private Map<Long, List<BookingPeriod>> bookings;
}
