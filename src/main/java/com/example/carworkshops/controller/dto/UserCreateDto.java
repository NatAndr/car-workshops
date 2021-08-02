package com.example.carworkshops.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Value
public class UserCreateDto {
    @NotBlank(message = "Name is mandatory")
    String name;

    @Email(message = "Email is invalid")
    String email;

    @NotNull(message = "City is mandatory")
    long cityId;

    @Size(max = 10)
    String postalCode;
}
