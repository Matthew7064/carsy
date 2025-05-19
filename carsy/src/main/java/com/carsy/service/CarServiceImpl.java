package com.carsy.service;

import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
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
            foundCar.setBrand(car.getBrand());
            foundCar.setModel(car.getModel());
            foundCar.setYear(car.getYear());
            foundCar.setValue(car.getValue());
            foundCar.setRentalPricePerDay(car.getRentalPricePerDay());
            return carRepository.save(foundCar);
        }
        return null;
    }

    @Override
    @Transactional
    public Car updateCar(Car car, long id) {
        Car foundCar = carRepository.findById(id).orElse(null);
        if (foundCar != null) {
            if (car.getBrand() != null) foundCar.setBrand(car.getBrand());
            if (car.getModel() != null) foundCar.setModel(car.getModel());
            if (car.getYear() > 0) foundCar.setYear(car.getYear());
            if (car.getValue() != null) foundCar.setValue(car.getValue());
            if (car.getRentalPricePerDay() != null) foundCar.setRentalPricePerDay(car.getRentalPricePerDay());
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
}
