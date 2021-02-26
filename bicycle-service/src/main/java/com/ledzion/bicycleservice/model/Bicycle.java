package com.ledzion.bicycleservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "bicycles")
public class Bicycle {

    @Id
    @NotBlank(message = "Bicycle Id should be provided.")
    private String id;

    @NotBlank(message = "Bicycle type should be provided.")
    private Type type;

    @NotBlank(message = "Bicycle size should be provided.")
    private Size size;

    private Map<String, List<BookingPeriod>> bookings;

    public Bicycle(String id, Type type, Size size) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.bookings = new HashMap<>();
    }
}
