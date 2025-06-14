package com.carsy.controller;

import com.carsy.model.User;
import com.carsy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        User user = userService.findUser(id);
        if (user != null) return ResponseEntity.ok(user);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable("id") UUID id, @RequestBody @Valid User user) {
        User updatedUser = userService.editUser(user, id);
        if (updatedUser != null) return ResponseEntity.ok(updatedUser);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid User user) {
        User updatedUser = userService.updateUser(user, id);
        if (updatedUser != null) return ResponseEntity.ok(updatedUser);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable("id") UUID id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}
