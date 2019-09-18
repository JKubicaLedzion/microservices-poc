package com.ledzion.bicycleservice.service;

import com.ledzion.bicycleservice.model.Bicycle;
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

    @Override
    public List<Bicycle> getBicyclesByTypeSize2(List<String> types, List<String> sizes) {
        return bicycleDAO.getBicyclesByTypeSize2(types, sizes);
    }

    public BicycleDAO getBicycleDAO() {
        return bicycleDAO;
    }

    public void setBicycleDAO(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }

    @Override
    public boolean findAndBookBicycle(long userId, String type, String size, LocalDate startDate,
            LocalDate endDate) {
        return bicycleDAO.findAndBookBicycle(userId, type, size, startDate, endDate);
    }

    @Override
    public boolean bookBicycle(long userId, long bicycleId, LocalDate startDate, LocalDate endDate) {
        return bicycleDAO.bookBicycle(userId, bicycleId, startDate, endDate);
    }

    @Override
    public boolean checkBicycleAvailability(long bicycleId, LocalDate startDate, LocalDate endDate) {
        return bicycleDAO.checkBicycleAvailability(bicycleId, startDate, endDate);
    }
}
