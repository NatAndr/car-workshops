package com.example.carworkshops.service.impl;

import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.repository.CarTrademarkRepository;
import com.example.carworkshops.service.CarTrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarTrademarkServiceImpl implements CarTrademarkService {
    private final CarTrademarkRepository carTrademarkRepository;

    @Autowired
    public CarTrademarkServiceImpl(CarTrademarkRepository carTrademarkRepository) {
        this.carTrademarkRepository = carTrademarkRepository;
    }

    @Override
    public CarTrademark getById(long id) {

        return carTrademarkRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new NoSuchElementException("Car Trademark not found, id = " + id));
    }

    @Override
    public List<CarTrademark> findAll() {
        return carTrademarkRepository.findAllByDeletedAtIsNull();
    }
}
