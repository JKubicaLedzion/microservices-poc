package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BicycleDAO {

    boolean bicycleAvailable(String id, LocalDate startDate, LocalDate endDate);

    Optional<Bicycle> getBicycleById(String id);

    List<Bicycle> getAllBicycles();

    boolean bookBicycle(Bicycle bicycle);

    List<Bicycle> getBicyclesByTypeSize(String type, String size);

    boolean addBicycle(Bicycle bicycle);
}
