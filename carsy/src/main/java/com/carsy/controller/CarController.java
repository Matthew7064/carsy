package com.carsy.controller;

import com.carsy.model.Car;
import com.carsy.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.findAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable ("id") Long id) {
        Car car = carService.findCar(id);
        if (car != null) return ResponseEntity.ok(car);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> editCar(@PathVariable ("id") Long id, @RequestBody Car car) {
        Car updatedCar = carService.editCar(car, id);
        if (updatedCar != null) return ResponseEntity.ok(updatedCar);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable ("id") Long id, @RequestBody Car car) {
        Car updatedCar = carService.updateCar(car, id);
        if (updatedCar != null) return ResponseEntity.ok(updatedCar);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable ("id") Long id) {
        carService.removeCar(id);
        return ResponseEntity.noContent().build();
    }
}
