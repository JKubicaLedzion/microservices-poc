package com.ledzion.bicycleservice.controller;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;
import com.ledzion.bicycleservice.service.BicycleService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bicycles")
public class BicycleController {

    private final Logger LOGGER = LoggerFactory.getLogger(BicycleController.class);

    private static final String BICYCLE_BOOKED = "Bicycle booked.";

    private static final String BICYCLE_ADDED = "Bicycle added.";

    private static final String BICYCLE_AVAILABLE = "Bicycle available.";

    private static final String BICYCLE_UNAVAILABLE = "Bicycle unavailable.";

    private static final String BICYCLE_NOT_FOUND = "Bicycle not found.";

    private static final String ERROR_WHILE_BOOKING_BICYCLE = "Error while booking bicycle. Provided data incorrect.";

    private static final String ERROR_WHILE_ADDING_BICYCLE = "Error while adding bicycle. Provided data incorrect.";

    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE =
            "No Response From Bicycle Service at this moment. " + " Service will be back shortly.";

    private BicycleService bicycleService;

    @Autowired
    public BicycleController(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    @HystrixCommand(fallbackMethod = "getBicycleByIdFallback")
    @GetMapping(value = "/{id}")
    public ResponseEntity getBicycleById(@PathVariable("id") String id) {
        LOGGER.debug("Getting bicycles with id {}.", id);
        Optional<Bicycle> bicycle = bicycleService.getBicycleById(id);
        return bicycle.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(bicycle.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_NOT_FOUND);
    }

    @HystrixCommand(fallbackMethod = "getAllBicyclesFallback")
    @GetMapping
    public ResponseEntity getAllBicycles() {
        LOGGER.debug("Getting all bicycles.");
        List<Bicycle> bicycles = bicycleService.getAllBicycles();
        return bicycles.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK).body(bicycles);
    }

    @HystrixCommand(fallbackMethod = "getBicyclesByTypeSizeFallback")
    @GetMapping(value = "filter")
    public ResponseEntity getBicyclesByTypeSize(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "size", required = false) String size) {
        LOGGER.debug("Getting bicycles of type {} and size {}.", type, size);
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize(type, size);
        return bicycles.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK).body(bicycles);
    }

    @HystrixCommand(fallbackMethod = "bookBicycleFallback")
    @PutMapping(value = "/booking")
    public ResponseEntity<String> bookBicycle(@RequestBody @Valid BookingParameters bookingParameters) {
        LOGGER.debug("Booking bicycles with id {} for customer {}.", bookingParameters.getBicycleId(),
                bookingParameters.getUserId());
        return bicycleService.bookBicycle(bookingParameters)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_WHILE_BOOKING_BICYCLE);
    }

    @HystrixCommand(fallbackMethod = "bicycleAvailableFallback")
    @GetMapping(value = "/{id}/availability")
    public ResponseEntity<String> bicycleAvailable(
            @PathVariable(name = "id") String id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "endDate") LocalDate endDate) {
        LOGGER.debug("Checking availability of bicycles with id {} for period: start date = {}, end date = {}.", id, startDate, endDate);
        return bicycleService.bicycleAvailable(id, startDate, endDate)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_AVAILABLE)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_UNAVAILABLE);
    }

        @HystrixCommand(fallbackMethod = "addBicycleFallback")
    @PostMapping
    public ResponseEntity<String> addBicycle(@RequestBody @Valid Bicycle bicycle) {
        LOGGER.debug("Adding bicycle with id {}.", bicycle.getId());
        return bicycleService.addBicycle(bicycle)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_ADDED)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_WHILE_ADDING_BICYCLE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getBicycleByIdFallback(String id) {
        return ResponseEntity.ok().body(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getAllBicyclesFallback() {
        return ResponseEntity.ok().body(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getBicyclesByTypeSizeFallback(String type, String size) {
        return ResponseEntity.ok().body(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<String> bookBicycleFallback(BookingParameters bookingParameters) {
        return ResponseEntity.ok().body(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<String> bicycleAvailableFallback(String bicycleId, LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok().body(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }
}
