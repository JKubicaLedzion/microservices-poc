package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface BicycleMongoDbRepository extends MongoRepository<Bicycle, String> {

    @Query(" { '$or' : [ { 'type' : ?0 } , { 'type' : ?1 } ] }")
    public List<Bicycle> findUsingQuery(String type, String size);

    public List<Bicycle> findByTypeAndSize(String type, String size);

    public List<Bicycle> findByType(String type);

    public List<Bicycle> findBySize(String size);

    @Query("{ id : ?0 }")
    public List<Bicycle> bicycleAvailable(String id, LocalDate startDate, LocalDate endDate);
}
