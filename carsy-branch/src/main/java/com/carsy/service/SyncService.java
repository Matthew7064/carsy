package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.dto.LocationDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;

import java.util.List;

public interface SyncService {
    void sendLocations(List<LocationDTO> locations);
    void sendCars(List<CarDTO> cars);
    void sendUsers(List<UserDTO> users);
    void sendOrders(List<OrderDTO> orders);
    void getBranchId(String url);
    UserDTO buildUserDTO(User user);
    CarDTO buildCarDTO(Car car);
    OrderDTO buildOrderDTO(Order order);
}
