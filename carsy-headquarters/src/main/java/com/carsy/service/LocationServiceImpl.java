package com.carsy.service;

import com.carsy.dto.LocationDTO;
import com.carsy.model.Location;
import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import com.carsy.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final CarRepository carRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, CarRepository carRepository) {
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
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
    public void removeLocation(UUID id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location findLocation(UUID id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }

    @Override
    public void syncLocations(List<LocationDTO> locations) {
        for (LocationDTO dto : locations) {
            Car car = carRepository.findById(dto.carId()).orElse(null);
            if (car != null) {
                Location location = new Location();
                location.setId(dto.id());
                location.setCar(car);
                location.setLatitude(dto.latitude());
                location.setLongitude(dto.longitude());
                location.setTime(dto.time());
                locationRepository.save(location);
            }
        }
    }
}
