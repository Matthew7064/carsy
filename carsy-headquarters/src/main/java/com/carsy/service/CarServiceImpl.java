package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.model.Branch;
import com.carsy.model.Location;
import com.carsy.model.car.Car;
import com.carsy.repository.BranchRepository;
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
    private final BranchRepository branchRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, BranchRepository branchRepository, LocationRepository locationRepository) {
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car editCar(Car car, UUID id) {
        Car foundCar = carRepository.findById(id).orElse(null);
        if (foundCar != null) {
            foundCar.setVin(car.getVin());
            foundCar.setRegistrationNumber(car.getRegistrationNumber());
            foundCar.setBrand(car.getBrand());
            if (car.getModel() != null) foundCar.setModel(car.getModel());
            if (car.getYear() > 0) foundCar.setYear(car.getYear());
            foundCar.setRentalPricePerDay(car.getRentalPricePerDay());
            if (car.getValue() != null) foundCar.setValue(car.getValue());
            foundCar.setCarStatus(car.getCarStatus());
            foundCar.setFuel(car.getFuel());
            foundCar.setTransmission(car.getTransmission());
            if (car.getMileage() > 0) foundCar.setMileage(car.getMileage());
            if (car.getHorsepower() > 0) foundCar.setHorsepower(car.getHorsepower());
            foundCar.setRegistrationDate(car.getRegistrationDate());
            foundCar.setInsuranceExpiryDate(car.getInsuranceExpiryDate());
            foundCar.setInspectionExpiryDate(car.getInspectionExpiryDate());
            updateLocations(car, foundCar);
            updateBranch(car, foundCar);
            return carRepository.save(foundCar);
        }
        return null;
    }

    @Override
    public Car updateCar(Car car, UUID id) {
        Car foundCar = carRepository.findById(id).orElse(null);
        if (foundCar != null) {
            if (car.getVin() != null) foundCar.setVin(car.getVin());
            if (car.getRegistrationNumber() != null) foundCar.setRegistrationNumber(car.getRegistrationNumber());
            if (car.getBrand() != null) foundCar.setBrand(car.getBrand());
            if (car.getModel() != null) foundCar.setModel(car.getModel());
            if (car.getYear() > 0) foundCar.setYear(car.getYear());
            if (car.getRentalPricePerDay() != null) foundCar.setRentalPricePerDay(car.getRentalPricePerDay());
            if (car.getValue() != null) foundCar.setValue(car.getValue());
            if (car.getCarStatus() != null) foundCar.setCarStatus(car.getCarStatus());
            if (car.getFuel() != null) foundCar.setFuel(car.getFuel());
            if (car.getTransmission() != null) foundCar.setTransmission(car.getTransmission());
            if (car.getMileage() > 0) foundCar.setMileage(car.getMileage());
            if (car.getHorsepower() > 0) foundCar.setHorsepower(car.getHorsepower());
            if (car.getRegistrationDate() != null) foundCar.setRegistrationDate(car.getRegistrationDate());
            if (car.getInsuranceExpiryDate() != null) foundCar.setInsuranceExpiryDate(car.getInsuranceExpiryDate());
            if (car.getInspectionExpiryDate() != null) foundCar.setInspectionExpiryDate(car.getInspectionExpiryDate());
            if (car.getBranch() != null) updateBranch(car, foundCar);
            if (car.getLocations() != null) updateLocations(car, foundCar);
            return carRepository.save(foundCar);
        }
        return null;
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void removeCar(UUID id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car findCar(UUID id) {
        Optional<Car> car = carRepository.findById(id);
        return car.orElse(null);
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

    private void updateBranch(Car car, Car foundCar) {
        Branch branch = car.getBranch();
        Branch attachedBranch = branchRepository.findById(branch.getId()).orElseThrow(() -> new IllegalArgumentException("Branch not found, id: " + branch.getId()));
        attachedBranch.getCars().add(foundCar);
        foundCar.setBranch(attachedBranch);
    }

    @Override
    public boolean assignToBranch(UUID carId, UUID branchId) {
        Car car = carRepository.findById(carId).orElse(null);
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (car != null && branch != null) {
            car.setBranch(branch);
            branch.getCars().add(car);
            carRepository.save(car);
            branchRepository.save(branch);
            return true;
        }
        return false;
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
    public List<UUID> syncCars(List<CarDTO> cars) {
        List<UUID> ids = new ArrayList<>();
        for (CarDTO dto : cars) {
            Car car = carRepository.findById(dto.id()).orElse(null);
            if (car != null) {
                car.setMileage(dto.mileage());
                car.setCarStatus(dto.carStatus());
                carRepository.save(car);
                ids.add(car.getId());
            }
        }
        return ids;
    }
}
