package com.example.carworkshops.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Value
public class WorkshopCreateDto {
    @NotBlank(message = "Name is mandatory")
    String name;

    @NotEmpty(message = "Input trademark list cannot be empty")
    List<Long> trademarkIds;

    @NotNull(message = "City is mandatory")
    long cityId;

    @Size(max = 10)
    String postalCode;
}
