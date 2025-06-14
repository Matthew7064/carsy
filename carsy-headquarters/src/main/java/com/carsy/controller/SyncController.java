package com.carsy.controller;

import com.carsy.dto.CarDTO;
import com.carsy.dto.LocationDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.service.CarService;
import com.carsy.service.LocationService;
import com.carsy.service.OrderService;
import com.carsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//for internal communication between headquarters and branches
@RestController
@RequestMapping("/sync")
public class SyncController {
    private final LocationService locationService;
    private final UserService userService;
    private final OrderService orderService;
    private final CarService carService;

    @Autowired
    public SyncController(LocationService locationService, UserService userService, OrderService orderService, CarService carService) {
        this.locationService = locationService;
        this.userService = userService;
        this.orderService = orderService;
        this.carService = carService;
    }

    @PostMapping("/locations")
    public ResponseEntity<Void> syncLocations(@RequestBody List<LocationDTO> locations) {
        locationService.syncLocations(locations);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> syncUsers(@RequestBody List<UserDTO> users) {
        userService.syncUsers(users);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> syncOrders(@RequestBody List<OrderDTO> orders) {
        orderService.syncOrders(orders);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cars")
    public ResponseEntity<Void> syncCars(@RequestBody List<CarDTO> cars) {
        carService.syncCars(cars);
        return ResponseEntity.ok().build();
    }
}
