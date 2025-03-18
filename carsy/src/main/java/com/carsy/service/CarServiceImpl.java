package com.carsy.service;

import com.carsy.model.Car;
import com.carsy.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void editCar(Car car) {
        carRepository.save(car);
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
        return carRepository.findById(id);
    }
}
