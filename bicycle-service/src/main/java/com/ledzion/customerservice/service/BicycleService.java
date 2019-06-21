package com.ledzion.customerservice.service;

import com.ledzion.customerservice.model.Bicycle;

import java.util.List;
import java.util.Optional;

public interface BicycleService {

    Optional<Bicycle> getBicycleById(long id);

    List<Bicycle> getAllBicycles();

    List<Bicycle> getBicyclesByType(String type, String size);
}
