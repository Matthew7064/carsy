package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.model.Location;
import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import com.carsy.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, LocationRepository locationRepository) {
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Car updateCar(Car car, UUID id) {
        Car foundCar = carRepository.findById(id).orElse(null);
        if (foundCar != null) {
            if (car.getCarStatus() != null) foundCar.setCarStatus(car.getCarStatus());
            if (car.getMileage() > 0) foundCar.setMileage(car.getMileage());
            if (car.getLocations() != null) updateLocations(car, foundCar);
            foundCar.setSynchronizedFlag(false);
            return carRepository.save(foundCar);
        }
        return null;
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car findCar(UUID id) {
        Optional<Car> car = carRepository.findById(id);
        return car.orElse(null);
    }

    @Override
    public void deleteCar(UUID id) {
        carRepository.deleteById(id);
    }

    private void updateLocations(Car car, Car foundCar) {
        List<Location> attachedLocations = new ArrayList<>();
        for (Location location : car.getLocations()) {
            Location attachedLocation = locationRepository.findById(location.getId()).orElseThrow(() -> new IllegalArgumentException("Location not found, id: " + location.getId()));
            attachedLocation.setCar(foundCar);
            attachedLocations.add(attachedLocation);
        }
        foundCar.setLocations(attachedLocations);
    }

    @Override
    public List<Location> getLocationsForCar(UUID carId, LocalDateTime since) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) return null;
        List<Location> allLocations = car.getLocations();
        List<Location> filtered = new ArrayList<>();
        for (Location location : allLocations) {
            if (since == null || location.getTime().isAfter(since)) {
                filtered.add(location);
            }
        }
        return filtered;
    }

    @Override
    public void syncCars(List<CarDTO> cars) {
        for (CarDTO dto : cars) {
            Car car = carRepository.findById(dto.id()).orElse(new Car());
            car.setId(dto.id());
            car.setCarStatus(dto.carStatus());
            car.setMileage(dto.mileage());
            car.setRentalPricePerDay(dto.rentalPricePerDay());
            car.setInspectionExpiryDate(dto.inspectionExpiryDate());
            car.setInsuranceExpiryDate(dto.insuranceExpiryDate());
            car.setVin(dto.vin());
            car.setRegistrationNumber(dto.registrationNumber());
            car.setBrand(dto.brand());
            car.setModel(dto.model());
            car.setYear(dto.year());
            car.setValue(dto.value());
            car.setFuel(dto.fuel());
            car.setTransmission(dto.transmission());
            car.setHorsepower(dto.horsepower());
            car.setRegistrationDate(dto.registrationDate());
            carRepository.save(car);
        }
    }
}
