package com.carsy.controller;

import com.carsy.model.Location;
import com.carsy.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.findAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id) {
        Location location = locationService.findLocation(id);
        if (location != null) return ResponseEntity.ok(location);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody @Valid Location location) {
        locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLocation(@PathVariable("id") Long id) {
        locationService.removeLocation(id);
        return ResponseEntity.noContent().build();
    }
}
