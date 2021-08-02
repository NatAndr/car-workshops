package com.example.carworkshops.service;

import com.example.carworkshops.controller.dto.AppointmentChangeDto;
import com.example.carworkshops.controller.dto.AppointmentCreateDto;
import com.example.carworkshops.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment create(AppointmentCreateDto dto);

    Appointment getById(long id);

    Appointment changeDateTime(long id, AppointmentChangeDto dto);

    void delete(long id);

    List<Appointment> findAll();
}
