package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;

import java.util.List;
import java.util.Optional;

public interface BicycleDAO {

    Optional<Bicycle> getBicycleById(long id);

    List<Bicycle> getAllBicycles();

    List<Bicycle> getBicyclesByType(String type, String size);
}
