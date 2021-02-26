package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.exceptions.BadRequest;
import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingPeriod;
import com.ledzion.bicycleservice.model.Size;
import com.ledzion.bicycleservice.model.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(name = "com.ledzion.bicycleservice.BicycleDAO", havingValue = "default")
public class BicycleDefaultDAO implements BicycleDAO {

    private static final String WRONG_BICYCLE_ID_PROVIDED = "Wrong bicycle Id provided.";
    private final List<Bicycle> bicycles = new ArrayList<>(Arrays.asList(
            new Bicycle("1", Type.CITY, Size.S),
            new Bicycle("2", Type.CROSS, Size.M),
            new Bicycle("3", Type.CITY, Size.M),
            new Bicycle("4", Type.MOUNTAIN, Size.M)
    ));

    @Override
    public Optional<Bicycle> getBicycleById(String id) {
        return getAllBicycles().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        return bicycles;
    }

    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return getAllBicycles().stream()
                .filter(b -> Objects.isNull(type) || Type.getType(type).equals(b.getType()))
                .filter(b -> Objects.isNull(size) || Size.getSize(size).equals(b.getSize()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean bookBicycle(Bicycle updatedBicycle) {
        Bicycle bicycle = getBicycleById(updatedBicycle.getId()).get();
        return bicycles.remove(bicycle) && bicycles.add(updatedBicycle);
    }

    @Override
    public boolean bicycleAvailable(String id, LocalDate startDate, LocalDate endDate) {
        Optional<Bicycle> bicycle = getBicycleById(id);
        if(!bicycle.isPresent()) {
            throw new BadRequest(WRONG_BICYCLE_ID_PROVIDED);
        }
        Map<String, List<BookingPeriod>> bookings = bicycle.get().getBookings();
        if(bookings == null || bookings.isEmpty()) {
            return true;
        }
        return bookings.values().stream()
                .flatMap(Collection::stream)
                .filter(b -> b.containsDate(startDate) || b.containsDate(endDate))
                .count() == 0;
    }

    @Override
    public boolean addBicycle(Bicycle bicycle) {
        return bicycles.add(bicycle);
    }
}
