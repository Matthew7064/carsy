package com.carsy.controller;

import com.carsy.dto.CarDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import com.carsy.repository.OrderRepository;
import com.carsy.service.CarService;
import com.carsy.service.SyncService;
import com.carsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//for internal communication between headquarters and branches
@RestController
@RequestMapping("/sync")
public class SyncController {
    private final UserService userService;
    private final CarService carService;
    private final OrderRepository orderRepository;
    private final SyncService syncService;

    @Autowired
    public SyncController(UserService userService, CarService carService, OrderRepository orderRepository, SyncService syncService) {
        this.userService = userService;
        this.carService = carService;
        this.orderRepository = orderRepository;
        this.syncService = syncService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> syncUsers(@RequestBody List<UserDTO> users) {
        userService.syncUsers(users);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserDTO(@PathVariable("id") UUID id) {
        User user = userService.findUser(id);
        if (user != null) {
            UserDTO dto = syncService.buildUserDTO(user);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDTO> getCarDTO(@PathVariable("id") UUID id) {
        Car car = carService.findCar(id);
        if (car != null) {
            CarDTO dto = syncService.buildCarDTO(car);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderDTO(@PathVariable("id") UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            OrderDTO dto = syncService.buildOrderDTO(order);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cars")
    public ResponseEntity<Void> syncCars(@RequestBody List<CarDTO> cars) {
        carService.syncCars(cars);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/orders/{id}/paid")
    public ResponseEntity<Void> markOrderAsPaid(@PathVariable("id") UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setPaid(true);
            orderRepository.save(order);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cars/{id}/delete")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID id) {
        Car car = carService.findCar(id);
        if (car != null) {
            carService.deleteCar(car.getId());
        }
        return ResponseEntity.ok().build();
    }
}
