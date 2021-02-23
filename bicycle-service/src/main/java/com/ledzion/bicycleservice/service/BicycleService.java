package com.ledzion.bicycleservice.service;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BicycleService {

    boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate);

    Optional<Bicycle> getBicycleById(long id);

    List<Bicycle> getAllBicycles();

    boolean bookBicycle(BookingParameters bookingParameters);

    List<Bicycle> getBicyclesByTypeSize(String type, String size);
}
