package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.BookingRequest;

public interface BookingService {

    boolean bookBicycle(BookingRequest bookingRequest);
}
