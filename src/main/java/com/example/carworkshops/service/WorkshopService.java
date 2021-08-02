package com.example.carworkshops.service;

import com.example.carworkshops.controller.dto.WorkshopCreateDto;
import com.example.carworkshops.model.Workshop;

import java.util.List;

public interface WorkshopService {
    Workshop create(WorkshopCreateDto dto);

    Workshop getById(long id);

    void delete(long id);

    List<Workshop> findByCity(long cityId);

    List<Workshop> findAll();
}
