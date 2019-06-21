package com.ledzion.customerservice.service;

import com.ledzion.customerservice.model.Bicycle;
import com.ledzion.customerservice.repository.BicycleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Bicycle> getBicyclesByType(String type, String size) {
        return bicycleDAO.getBicyclesByType(type, size);
    }

    public BicycleDAO getBicycleDAO() {
        return bicycleDAO;
    }

    public void setBicycleDAO(BicycleDAO bicycleDAO) {
        this.bicycleDAO = bicycleDAO;
    }
}
