package com.ledzion.bicycleservice.service;

import com.ledzion.bicycleservice.exceptions.BadRequest;
import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;
import com.ledzion.bicycleservice.model.BookingPeriod;
import com.ledzion.bicycleservice.model.Size;
import com.ledzion.bicycleservice.model.Type;
import com.ledzion.bicycleservice.repository.BicycleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BicycleServiceImpl implements BicycleService {

    private BicycleDAO bicycleDAO;

    private static final String END_DATE_IS_AFTER_START_DATE = "End date is after start date.";

    private static final String BICYCLE_UNAVAILABLE = "Bicycle unavailable.";

    @Autowired
    public BicycleServiceImpl(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }

    @Override
    public Optional<Bicycle> getBicycleById(String id) {
        return bicycleDAO.getBicycleById(id);
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        return bicycleDAO.getAllBicycles();
    }

    @Override
    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return bicycleDAO.getBicyclesByTypeSize(type, size);
    }

    @Override
    public boolean bookBicycle(BookingParameters bookingParameters) {
        validateBookingDates(bookingParameters);

        if (!getBicycleById(bookingParameters.getBicycleId()).isPresent()) {
            return false;
        }

        Bicycle bicycle = getBicycleById(bookingParameters.getBicycleId()).get();
        BookingPeriod bookingPeriod = new BookingPeriod(bookingParameters.getStartDate(), bookingParameters.getEndDate());

        Map<String, List<BookingPeriod>> bicycleBookings = bicycle.getBookings();
        if(bicycleBookings == null || bicycleBookings.isEmpty()) {
            bicycle.setBookings(new HashMap<>());
        } else {
            if (!bicycleAvailable(bookingParameters.getBicycleId(), bookingParameters.getStartDate(),
                    bookingParameters.getEndDate())) {
                throw new BadRequest(BICYCLE_UNAVAILABLE);
            }
        }

        List<BookingPeriod> customerBookings = bicycle.getBookings().get(bookingParameters.getUserId());
        if(customerBookings == null || customerBookings.isEmpty()) {
            bicycle.getBookings().put(bookingParameters.getUserId(), new ArrayList<>(Arrays.asList(bookingPeriod)));
        } else {
            customerBookings.add(bookingPeriod);
            bicycle.getBookings().put(bookingParameters.getBicycleId(), customerBookings);
        }

        return bicycleDAO.bookBicycle(bicycle);
    }

    @Override
    public boolean bicycleAvailable(String id, LocalDate startDate, LocalDate endDate) {
        return bicycleDAO.bicycleAvailable(id, startDate, endDate);
    }

    @Override
    public boolean addBicycle(Bicycle bicycle) {
        return bicycleDAO.addBicycle(bicycle);
    }

    private void validateBookingDates(BookingParameters bookingParameters) {
        if(bookingParameters.getStartDate().isAfter(bookingParameters.getEndDate())) {
            throw new BadRequest(END_DATE_IS_AFTER_START_DATE);
        };
    }

    public BicycleDAO getBicycleDAO() {
        return bicycleDAO;
    }

    public void setBicycleDAO(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }
}
