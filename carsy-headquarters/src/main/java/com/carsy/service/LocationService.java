package com.carsy.service;

import com.carsy.dto.LocationDTO;
import com.carsy.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService {
    void addLocation(Location location);
    List<Location> findAllLocations();
    void removeLocation(UUID id);
    Location findLocation(UUID id);
    List<UUID> syncLocations(List<LocationDTO> locations);
}
