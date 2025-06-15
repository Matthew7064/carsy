package com.carsy.controller;

import com.carsy.dto.CarDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Order;
import com.carsy.repository.OrderRepository;
import com.carsy.service.CarService;
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

    @Autowired
    public SyncController(UserService userService, CarService carService, OrderRepository orderRepository) {
        this.userService = userService;
        this.carService = carService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> syncUsers(@RequestBody List<UserDTO> users) {
        userService.syncUsers(users);
        return ResponseEntity.ok().build();
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
}
