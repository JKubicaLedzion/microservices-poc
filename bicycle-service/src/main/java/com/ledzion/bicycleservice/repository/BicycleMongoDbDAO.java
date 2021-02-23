package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@Repository
public class BicycleMongoDbDAO implements BicycleDAO {

    @Override
    public Optional<Bicycle> getBicycleById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        return null;
    }

    @Override
    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return null;
    }

    @Override
    public boolean bookBicycle(BookingParameters bookingParameters) {
        return false;
    }

    @Override
    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
