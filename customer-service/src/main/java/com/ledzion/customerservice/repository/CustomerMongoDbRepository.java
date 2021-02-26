package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomerMongoDbRepository extends MongoRepository<Customer, String> {
}
