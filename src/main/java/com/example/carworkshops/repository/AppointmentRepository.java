package com.example.carworkshops.repository;

import com.example.carworkshops.model.Appointment;
import com.example.carworkshops.repository.base.BaseRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface AppointmentRepository extends BaseRepository<Appointment, Long> {

    List<Appointment> findByUser_idAndAppointmentAtAndDeletedAtIsNull(long user_id, ZonedDateTime appointmentAt);

}
