package com.example.carworkshops.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder
@Value
public class CityCreateDto {
    @NotBlank(message = "Name is mandatory")
    String name;
}
