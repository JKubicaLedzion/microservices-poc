package com.ledzion.bicycleservice.repository;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;
import com.ledzion.bicycleservice.model.BookingPeriod;
import com.ledzion.bicycleservice.model.Size;
import com.ledzion.bicycleservice.model.Type;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BicycleDefaultDAO implements BicycleDAO {

    private final List<Bicycle> bicycles = new ArrayList<>(Arrays.asList(
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

    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return getAllBicycles().stream()
                .filter(b -> Objects.isNull(type) || Type.getType(type).equals(b.getType()))
                .filter(b -> Objects.isNull(size) || Size.getSize(size).equals(b.getSize()))
                .collect(Collectors.toList());
    }

    public List<Bicycle> getBicyclesByTypesSizes(List<String> types, List<String> sizes) {
        return getAllBicycles().stream()
                .filter(b -> Objects.isNull(types) || types.contains(b.getType().getTypeDescription()))
                .filter(b -> Objects.isNull(sizes) || sizes.contains(b.getSize().getSizeDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean bookBicycle(BookingParameters bookingParameters) {
        //todo: add specific error
        Optional<Bicycle> bicycle = getBicycleById(bookingParameters.getBicycleId());
        if (!bicycle.isPresent()) {
            return false;
        }
        List<BookingPeriod> bookings = bicycle.get().getBookings().values().stream()
                .flatMap(Collection::stream)
                .filter(b -> b.containsDate(bookingParameters.getStartDate())
                        || b.containsDate(bookingParameters.getEndDate()))
                .collect(Collectors.toList());
        if (!bookings.isEmpty()) {
            return false;
        }
        List<BookingPeriod> updatedBookings = bicycle.get().getBookings().get(bookingParameters.getUserId());
        if(updatedBookings == null || updatedBookings.isEmpty()) {
            updatedBookings = new ArrayList<>();
        }
        updatedBookings.add(new BookingPeriod(bookingParameters.getStartDate(), bookingParameters.getEndDate()));
        bicycle.get().getBookings().put(bookingParameters.getUserId(), updatedBookings);
        return true;
    }

    @Override
    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return getBicycleById(id).map(value -> value.getBookings().values().stream()
                .flatMap(Collection::stream)
                .filter(b -> b.containsDate(startDate) || b.containsDate(endDate))
                .count() == 0).orElse(false);
    }
}
