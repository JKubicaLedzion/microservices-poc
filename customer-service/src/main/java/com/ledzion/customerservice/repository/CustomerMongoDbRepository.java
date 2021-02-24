package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public interface CustomerMongoDbRepository extends MongoRepository<Customer, String> {
}
