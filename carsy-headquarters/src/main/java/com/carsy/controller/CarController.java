package com.carsy.controller;

import com.carsy.model.Location;
import com.carsy.model.car.Car;
import com.carsy.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
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
    public ResponseEntity<Car> getCarById(@PathVariable ("id") UUID id) {
        Car car = carService.findCar(id);
        if (car != null) return ResponseEntity.ok(car);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody @Valid Car car) {
        carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> editCar(@PathVariable ("id") UUID id, @RequestBody @Valid Car car) {
        Car updatedCar = carService.editCar(car, id);
        if (updatedCar != null) return ResponseEntity.ok(updatedCar);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable ("id") UUID id, @RequestBody Car car) {
        Car updatedCar = carService.updateCar(car, id);
        if (updatedCar != null) return ResponseEntity.ok(updatedCar);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable ("id") UUID id) {
        carService.removeCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{carId}/assign/{branchId}")
    public ResponseEntity<Void> assignCarToBranch(@PathVariable("carId") UUID carId, @PathVariable("branchId") UUID branchId) {
        boolean success = carService.assignToBranch(carId, branchId);
        if (success) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<Location>> getCarLocations(
            @PathVariable UUID id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        List<Location> locations = carService.getLocationsForCar(id, since);
        if (locations != null) return ResponseEntity.ok(locations);
        else return ResponseEntity.notFound().build();
    }
}
