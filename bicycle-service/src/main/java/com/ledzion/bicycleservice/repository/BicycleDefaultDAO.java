package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingPeriod;
import com.ledzion.bicycleservice.model.Size;
import com.ledzion.bicycleservice.model.Type;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BicycleDefaultDAO implements BicycleDAO {

    private List<Bicycle> bicycles = new ArrayList<>(Arrays.asList(
            new Bicycle(1, Type.CITY, Size.S),
            new Bicycle(2, Type.CROSS, Size.M),
            new Bicycle(3, Type.CITY, Size.M),
            new Bicycle(4, Type.MOUNTAIN, Size.M)
    ));

    @Override
    public Optional<Bicycle> getBicycleById(long id) {
        return getAllBicycles().stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        return bicycles;
    }

    @Override
    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return getAllBicycles().stream()
                .filter(b -> Objects.isNull(type) || Type.getType(type).equals(b.getType()))
                .filter(b -> Objects.isNull(size) || Size.getSize(size).equals(b.getSize()))
                .collect(Collectors.toList());
    }

    public List<Bicycle> getBicyclesByTypeSize2(List<String> types, List<String> sizes) {
        return getAllBicycles().stream()
                .filter(b -> Objects.isNull(types) || types.contains(b.getType().getTypeDescription()))
                .filter(b -> Objects.isNull(sizes) || sizes.contains(b.getSize().getSizeDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean findAndBookBicycle(long userId, String type, String size, LocalDate startDate, LocalDate endDate) {
        //todo: add specific error
        Bicycle bicycle = getBicyclesByTypeSize(type, size).get(0);
        if(Objects.isNull(bicycle)) {
            return false;
        }
        List<BookingPeriod> bookings = bicycle.getBookings().values().stream()
                .filter(b -> b.containsDate(startDate) || b.containsDate(endDate))
                .collect(Collectors.toList());
        if(!bookings.isEmpty()) {
            return false;
        }
        BookingPeriod bookingPeriod = new BookingPeriod(startDate, endDate);
        bicycle.getBookings().put(userId, bookingPeriod);
        return true;
    }

    @Override
    public boolean bookBicycle(long userId, long bicycleId, LocalDate startDate, LocalDate endDate) {
        //todo: add specific error
        Optional<Bicycle> bicycle = getBicycleById(bicycleId);
        if(!bicycle.isPresent()) {
            return false;
        }
        List<BookingPeriod> bookings = bicycle.get().getBookings().values().stream()
                .filter(b -> b.containsDate(startDate) || b.containsDate(endDate))
                .collect(Collectors.toList());
        if(!bookings.isEmpty()) {
            return false;
        }
        BookingPeriod bookingPeriod = new BookingPeriod(startDate, endDate);
        bicycle.get().getBookings().put(userId, bookingPeriod);
        return true;
    }
}
