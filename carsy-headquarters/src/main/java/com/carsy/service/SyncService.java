package com.carsy.service;

import com.carsy.dto.CarDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;

public interface SyncService {
    UserDTO buildUserDTO(User user);
    CarDTO buildCarDTO(Car car);
    OrderDTO buildOrderDTO(Order order);
}
