package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.model.Location;
import com.carsy.model.car.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CarService {
    Car updateCar(Car car, UUID id);
    List<Car> findAllCars();
    Car findCar(UUID id);
    List<Location> getLocationsForCar(UUID carId, LocalDateTime since);
    void syncCars(List<CarDTO> cars);
}
