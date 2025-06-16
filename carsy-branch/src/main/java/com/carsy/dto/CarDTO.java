package com.carsy.dto;

import com.carsy.model.car.CarStatus;
import com.carsy.model.car.Fuel;
import com.carsy.model.car.Transmission;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CarDTO(
        UUID id,
        CarStatus carStatus,
        int mileage,
        BigDecimal rentalPricePerDay,
        LocalDate insuranceExpiryDate,
        LocalDate inspectionExpiryDate,
        String vin,
        String registrationNumber,
        String brand,
        String model,
        int year,
        BigDecimal value,
        Fuel fuel,
        Transmission transmission,
        int horsepower,
        LocalDate registrationDate
) {}
