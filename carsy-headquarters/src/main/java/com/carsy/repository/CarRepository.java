package com.carsy.repository;

import com.carsy.model.car.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Transactional
@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    Car findByVin(String vin);
    Car findByRegistrationNumber(String registrationNumber);
}
