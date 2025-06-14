package com.carsy.dto;

import com.carsy.model.car.CarStatus;

import java.util.UUID;

public record CarDTO(
        UUID id,
        CarStatus carStatus,
        int mileage
) {}
