package com.carsy.service;

import com.carsy.model.Branch;
import com.carsy.model.Location;
import com.carsy.model.car.Car;
import com.carsy.repository.BranchRepository;
import com.carsy.repository.CarRepository;
import com.carsy.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional
    public Car editCar(Car car, long id) {
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
    @Transactional
    public Car updateCar(Car car, long id) {
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
    @Transactional
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public void removeCar(long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Car findCar(long id) {
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
}
