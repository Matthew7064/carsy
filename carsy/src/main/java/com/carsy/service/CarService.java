package com.carsy.service;

import com.carsy.model.Car;

import java.util.List;

public interface CarService {
    void addCar(Car car);
    void editCar(Car car);
    List<Car> findAllCars();
    void removeCar(long id);
    Car findCar(long id);
}
