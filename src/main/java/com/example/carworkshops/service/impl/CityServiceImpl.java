package com.example.carworkshops.service.impl;

import com.example.carworkshops.model.City;
import com.example.carworkshops.repository.CityRepository;
import com.example.carworkshops.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City getById(long id) {
        return cityRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new NoSuchElementException("City not found, id = " + id));
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAllByDeletedAtIsNull();
    }
}
