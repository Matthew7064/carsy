package com.carsy.service;

import com.carsy.model.Car;

import java.util.List;

public interface CarService {
    void addCar(Car car);
    Car editCar(Car car, long id);
    Car updateCar(Car car, long id);
    List<Car> findAllCars();
    void removeCar(long id);
    Car findCar(long id);
}
