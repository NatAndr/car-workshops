package com.example.carworkshops.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Builder
@Value
public class AppointmentCreateDto {
    @NotNull(message = "User is mandatory")
    long userId;

    @NotNull(message = "Workshop is mandatory")
    long workshopId;

    @NotNull(message = "Car trademark is mandatory")
    long carTrademarkId;

    @Future
    @NotNull(message = "Appointment at is mandatory")
    ZonedDateTime appointmentAt;
}
