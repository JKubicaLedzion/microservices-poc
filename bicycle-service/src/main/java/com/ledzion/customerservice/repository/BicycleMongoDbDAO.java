package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Bicycle;

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
    public List<Bicycle> getBicyclesByType(String type, String size) {
        return null;
    }
}
