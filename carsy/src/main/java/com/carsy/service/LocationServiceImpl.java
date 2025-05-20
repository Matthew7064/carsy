package com.carsy.service;

import com.carsy.model.Location;
import com.carsy.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public void removeLocation(long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location findLocation(long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }
}
