package com.ledzion.bicycleservice.controller;

import com.ledzion.bicycleservice.model.Bicycle;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bicycles")
public class BicycleController {

    private final Logger LOGGER = LoggerFactory.getLogger(BicycleController.class);

    private static final String BICYCLE_BOOKED = "Bicycle booked.";

    private static final String BICYCLE_AVAILABLE = "Bicycle available.";

    private static final String BICYCLE_UNAVAILABLE = "Bicycle unavailable.";

    private static final String BICYCLE_NOT_FOUND = "Bicycle not found.";

    private static final String ERROR_WHILE_BOOKING_BICYCLE = "Error while booking bicycle. Provided data incorrect.";

    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE =
            "No Response From Bicycle Service at this moment. " + " Service will be back shortly.";

    private BicycleService bicycleService;

    @Autowired
    public BicycleController(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    @HystrixCommand(fallbackMethod = "getBicycleByIdFallback")
    @GetMapping(value = "/{id}")
    public ResponseEntity getBicycleById(@PathVariable("id") long id) {
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

    @HystrixCommand(fallbackMethod = "getBicyclesByTypeSizeFallback2")
    @GetMapping(value = "multi-filter")
    public ResponseEntity getBicyclesByTypeSize2(
            @RequestParam(name = "type", required = false) List<String> types,
            @RequestParam(name = "size", required = false) List<String> sizes) {
        LOGGER.debug("Getting bicycles of type {} and size {}.", types, sizes);
        List<Bicycle> bicycles = bicycleService.getBicyclesByTypeSize2(types, sizes);
        return bicycles.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK).body(bicycles);
    }

    @HystrixCommand(fallbackMethod = "findAndBookBicycleFallback")
    @PostMapping
    public ResponseEntity findAndBookBicycle(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "size", required = false) String size,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "endDate") LocalDate endDate) {
        LOGGER.debug("Booking bicycles of type {} and size {} for customer {}.", type, size, userId);
        return bicycleService.findAndBookBicycle(userId, type, size, startDate, endDate)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_WHILE_BOOKING_BICYCLE);
    }

    @HystrixCommand(fallbackMethod = "bookBicycleFallback")
    @PostMapping(value = "/{id}/booking/{userId}/{startDate}/{endDate}")
    public ResponseEntity bookBicycle(
            @PathVariable("userId") long userId,
            @PathVariable("id") long bicycleId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("endDate") LocalDate endDate) {
        LOGGER.debug("Booking bicycles with id {} for customer {}.", bicycleId, userId);
        return bicycleService.bookBicycle(userId, bicycleId, startDate, endDate)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_BOOKED)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_WHILE_BOOKING_BICYCLE);
    }

    @HystrixCommand(fallbackMethod = "bicycleAvailableFallback")
    @GetMapping(value = "/{id}/availability")
    public ResponseEntity bicycleAvailable(
            @PathVariable(name = "id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "endDate") LocalDate endDate) {
        LOGGER.debug("Checking availability of bicycles with id {} for period: stary date = {}, end date = {}.", id, startDate, endDate);
        return bicycleService.bicycleAvailable(id, startDate, endDate)
                ? ResponseEntity.status(HttpStatus.OK).body(BICYCLE_AVAILABLE)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(BICYCLE_UNAVAILABLE);
    }

    @SuppressWarnings("unused")
    public ResponseEntity getBicycleByIdFallback(@PathVariable("id") long id) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity getAllBicyclesFallback() {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity getBicyclesByTypeSizeFallback(@RequestParam(name = "type") String type,
            @RequestParam(name = "size", required = false) String size) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity getBicyclesByTypeSizeFallback2(@RequestParam(name = "type") List<String> type,
            @RequestParam(name = "size", required = false) List<String> size) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity findAndBookBicycleFallback(long userId, String type, String size, LocalDate startDate,
            LocalDate endDate) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity bookBicycleFallback(long userId, long bicycleId, LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }

    @SuppressWarnings("unused")
    public ResponseEntity bicycleAvailableFallback(long bicycleId, LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok().body( SERVICE_UNAVAILABLE_ERROR_MESSAGE );
    }
}
