package com.carsy.dto;

import com.carsy.model.car.CarStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CarDTO(
        UUID id,
        CarStatus carStatus,
        int mileage,
        BigDecimal rentalPricePerDay,
        LocalDate insuranceExpiryDate,
        LocalDate inspectionExpiryDate
) {}
