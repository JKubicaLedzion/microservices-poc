package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;

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
    public List<Bicycle> getBicyclesByTypeSize2( List<String> type, List<String> size )
    {
        return null;
    }

    @Override
    public boolean findAndBookBicycle(long userId, String type, String size, LocalDate startDate,
            LocalDate endDate) {
        return false;
    }

    @Override
    public boolean bookBicycle(long userId, long id, LocalDate startDate, LocalDate endDate) {
        return false;
    }

    @Override
    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
