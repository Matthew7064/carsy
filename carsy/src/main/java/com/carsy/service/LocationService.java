package com.carsy.service;

import com.carsy.model.Location;

import java.util.List;

public interface LocationService {
    void addLocation(Location location);
    List<Location> findAllLocations();
    void removeLocation(long id);
    Location findLocation(long id);
}
