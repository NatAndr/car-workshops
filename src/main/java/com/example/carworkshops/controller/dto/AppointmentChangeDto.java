package com.example.carworkshops.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Builder
@Value
public class AppointmentChangeDto {
    @Future
    @NotNull(message = "Appointment at is mandatory")
    ZonedDateTime appointmentAt;
}
