package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.model.Location;
import com.carsy.model.car.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CarService {
    void addCar(Car car);
    Car editCar(Car car, UUID id);
    Car updateCar(Car car, UUID id);
    List<Car> findAllCars();
    void removeCar(UUID id);
    Car findCar(UUID id);
    boolean assignToBranch(UUID carId, UUID branchId);
    List<Location> getLocationsForCar(UUID carId, LocalDateTime since);
    void syncCars(List<CarDTO> cars);
}
