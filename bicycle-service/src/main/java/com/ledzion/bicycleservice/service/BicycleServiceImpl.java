package com.ledzion.bicycleservice.service;

import com.ledzion.bicycleservice.model.Bicycle;
import com.ledzion.bicycleservice.model.BookingParameters;
import com.ledzion.bicycleservice.repository.BicycleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BicycleServiceImpl implements BicycleService {

    private BicycleDAO bicycleDAO;

    @Autowired
    public BicycleServiceImpl(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }

    @Override
    public Optional<Bicycle> getBicycleById(long id) {
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

    public BicycleDAO getBicycleDAO() {
        return bicycleDAO;
    }

    public void setBicycleDAO(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }

    @Override
    public boolean bookBicycle(BookingParameters bookingParameters) {
        return bicycleDAO.bookBicycle(bookingParameters);
    }

    @Override
    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return bicycleDAO.bicycleAvailable(id, startDate, endDate);
    }
}
