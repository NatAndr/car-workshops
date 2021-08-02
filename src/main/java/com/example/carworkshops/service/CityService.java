package com.example.carworkshops.service;

import com.example.carworkshops.model.City;

import java.util.List;

public interface CityService {
    City getById(long id);

    List<City> findAll();
}
