package com.carsy.service;

import com.carsy.dto.AddressDTO;
import com.carsy.dto.CarDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Address;
import com.carsy.model.Order;
import com.carsy.model.Role;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SyncServiceImpl implements SyncService {
    @Override
    public UserDTO buildUserDTO(User user) {
        Address address = user.getAddress();
        AddressDTO addressDTO = new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getFlatNumber(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry()
        );
        Set<String> roles = new HashSet<>();
        for (Role role: user.getRoles()) {
            roles.add(role.getRole());
        }
        return new UserDTO(
                user.getId(),
                user.getPesel(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAccountNumber(),
                user.getLogin(),
                roles,
                addressDTO,
                user.getBranches().iterator().next().getId()
        );
    }

    @Override
    public CarDTO buildCarDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getCarStatus(),
                car.getMileage(),
                car.getRentalPricePerDay(),
                car.getInsuranceExpiryDate(),
                car.getInspectionExpiryDate(),
                car.getVin(),
                car.getRegistrationNumber(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getValue(),
                car.getFuel(),
                car.getTransmission(),
                car.getHorsepower(),
                car.getRegistrationDate()
        );
    }

    @Override
    public OrderDTO buildOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCar().getId(),
                order.getUser().getId(),
                order.isPaid(),
                order.getStartDate(),
                order.getEndDate(),
                order.getPrice()
        );
    }
}
