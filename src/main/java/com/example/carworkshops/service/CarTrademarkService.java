package com.example.carworkshops.service;

import com.example.carworkshops.model.CarTrademark;

import java.util.List;

public interface CarTrademarkService {
    CarTrademark getById(long id);

    List<CarTrademark> findAll();
}
