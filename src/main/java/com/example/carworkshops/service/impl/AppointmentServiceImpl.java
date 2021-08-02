package com.example.carworkshops.service.impl;

import com.example.carworkshops.controller.dto.AppointmentChangeDto;
import com.example.carworkshops.controller.dto.AppointmentCreateDto;
import com.example.carworkshops.model.Appointment;
import com.example.carworkshops.model.CarTrademark;
import com.example.carworkshops.model.User;
import com.example.carworkshops.model.Workshop;
import com.example.carworkshops.repository.AppointmentRepository;
import com.example.carworkshops.service.AppointmentService;
import com.example.carworkshops.service.CarTrademarkService;
import com.example.carworkshops.service.UserService;
import com.example.carworkshops.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final WorkshopService workshopService;
    private final CarTrademarkService carTrademarkService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService, WorkshopService workshopService, CarTrademarkService carTrademarkService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.workshopService = workshopService;
        this.carTrademarkService = carTrademarkService;
    }

    @Override
    public Appointment create(AppointmentCreateDto dto) {
        User user = userService.getById(dto.getUserId());
        Workshop workshop = workshopService.getById(dto.getWorkshopId());
        CarTrademark carTrademark = carTrademarkService.getById(dto.getCarTrademarkId());

        Appointment appointment = Appointment.builder()
            .user(user)
            .workshop(workshop)
            .carTrademark(carTrademark)
            .appointmentAt(dto.getAppointmentAt())
            .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getById(long id) {
        return appointmentRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new NoSuchElementException("Appointment not found, id = " + id));
    }

    @Override
    public Appointment changeDateTime(long id, AppointmentChangeDto dto) {
        Appointment appointment = getById(id);

        Appointment updated = appointment.toBuilder()
            .appointmentAt(dto.getAppointmentAt())
            .updatedAt(ZonedDateTime.now())
            .build();

        return appointmentRepository.save(updated);
    }

    @Override
    public void delete(long id) {
        Appointment appointment = getById(id);

        appointment.delete();
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAllByDeletedAtIsNull();
    }

}
