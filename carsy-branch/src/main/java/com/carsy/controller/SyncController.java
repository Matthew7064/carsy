package com.carsy.controller;

import com.carsy.dto.CarDTO;
import com.carsy.dto.UserDTO;
import com.carsy.service.CarService;
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
    private final UserService userService;
    private final CarService carService;

    @Autowired
    public SyncController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
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
}
