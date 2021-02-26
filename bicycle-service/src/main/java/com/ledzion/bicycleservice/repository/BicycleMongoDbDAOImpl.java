package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.exceptions.BadRequest;
import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "com.ledzion.bicycleservice.BicycleDAO", havingValue = "MongoDb")
public class BicycleMongoDbDAOImpl implements  BicycleDAO{

    private static final String WRONG_BICYCLE_ID_PROVIDED = "Wrong bicycle Id provided.";

    @Autowired
    private BicycleMongoDbRepository bicycleMongoDbRepository;

    @Override
    public List<Bicycle> getAllBicycles() {
        return bicycleMongoDbRepository.findAll();
    }

    //TODO
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
    public Optional<Bicycle> getBicycleById(String id) {
        return Optional.ofNullable(bicycleMongoDbRepository.findOne(id));
    }

    @Override
    public boolean bookBicycle(Bicycle bicycle) {
        return bicycleMongoDbRepository.save(bicycle).equals(bicycle);
    }

    @Override
    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        if(type == null) {
            if(size == null) {
                return bicycleMongoDbRepository.findAll();
            } else {
                return bicycleMongoDbRepository.findBySize(size);
            }
        }
        if(size == null) {
            return bicycleMongoDbRepository.findByType(type);
        }
        return bicycleMongoDbRepository.findByTypeAndSize(type, size);
    }

    @Override
    public boolean addBicycle(Bicycle bicycle) {
        return bicycleMongoDbRepository.save(bicycle).equals(bicycle);
    }
}
