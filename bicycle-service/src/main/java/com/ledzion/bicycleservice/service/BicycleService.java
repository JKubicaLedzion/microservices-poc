package com.ledzion.bicycleservice.service;

import com.ledzion.bicycleservice.model.Bicycle;

import java.util.List;
import java.util.Optional;

public interface BicycleService {

    Optional<Bicycle> getBicycleById(long id);

    List<Bicycle> getAllBicycles();

    List<Bicycle> getBicyclesByType(String type, String size);
}
