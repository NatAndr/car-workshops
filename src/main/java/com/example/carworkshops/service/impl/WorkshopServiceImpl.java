package com.example.carworkshops.service.impl;

import com.example.carworkshops.controller.dto.WorkshopCreateDto;
import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.model.City;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.repository.WorkshopRepository;
import com.example.carworkshops.service.CarTrademarkService;
import com.example.carworkshops.service.CityService;
import com.example.carworkshops.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkshopServiceImpl implements WorkshopService {
    private final WorkshopRepository workshopRepository;
    private final CarTrademarkService carTrademarkService;
    private final CityService cityService;

    @Autowired
    public WorkshopServiceImpl(WorkshopRepository workshopRepository,
                               CarTrademarkService carTrademarkService,
                               CityService cityService) {
        this.workshopRepository = workshopRepository;
        this.carTrademarkService = carTrademarkService;
        this.cityService = cityService;
    }

    @Override
    public Workshop create(WorkshopCreateDto dto) {
        Set<CarTrademark> carTrademarks = dto.getTrademarkIds().stream()
            .map(carTrademarkService::getById)
            .collect(Collectors.toSet());

        City city = cityService.getById(dto.getCityId());

        Workshop workshop = Workshop.builder()
            .name(dto.getName())
            .trademarks(carTrademarks)
            .city(city)
            .postalCode(dto.getPostalCode())
            .build();

        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop getById(long id) {

        return workshopRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new NoSuchElementException("Workshop not found, id = " + id));
    }

    @Override
    public void delete(long id) {
        Workshop workshop = getById(id);

        workshop.delete();
        workshopRepository.save(workshop);
    }

    @Override
    public List<Workshop> findByCity(long cityId) {

        return workshopRepository
            .findByCity_IdAndDeletedAtIsNull(cityId);
    }

    @Override
    public List<Workshop> findAll() {

        return workshopRepository.findAllByDeletedAtIsNull();
    }
}
