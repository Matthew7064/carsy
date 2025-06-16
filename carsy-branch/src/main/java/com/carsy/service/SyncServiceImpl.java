package com.carsy.service;

import com.carsy.dto.*;
import com.carsy.model.*;
import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import com.carsy.repository.LocationRepository;
import com.carsy.repository.OrderRepository;
import com.carsy.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class SyncServiceImpl implements SyncService {
    public static final String HEADQUARTERS_URL = "http://localhost:8080";
    public static UUID BRANCH_ID;
    private final WebClient webClient;
    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public SyncServiceImpl(LocationRepository locationRepository, CarRepository carRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.webClient = WebClient.create(HEADQUARTERS_URL);
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init() {
        String url = System.getProperty("URL");
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Environment variable or system property 'URL' must be set to a valid application url.");
        }
        getBranchId(url);
        System.out.println("Branch id: " + BRANCH_ID);
    }

    @Override
    public void sendLocations(List<LocationDTO> locations) {
        List<UUID> ids = webClient.post()
                .uri("/sync/locations")
                .bodyValue(locations)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {})
                .doOnSuccess(response -> System.out.println("Locations sent to headquarters."))
                .doOnError(error -> System.err.println("Error while sending locations to headquarters: " + error.getMessage()))
                .block();

        if (ids != null && !ids.isEmpty()) {
            List<Location> synchronizedLocations = locationRepository.findAllById(ids);
            for (Location location : synchronizedLocations) {
                location.setSynchronizedFlag(true);
            }
            locationRepository.saveAll(synchronizedLocations);
        }
    }

    @Override
    public void sendCars(List<CarDTO> cars) {
        List<UUID> ids = webClient.post()
                .uri("/sync/cars")
                .bodyValue(cars)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {})
                .doOnSuccess(response -> System.out.println("Cars sent to headquarters."))
                .doOnError(error -> System.err.println("Error while sending cars to headquarters: " + error.getMessage()))
                .block();

        if (ids != null && !ids.isEmpty()) {
            List<Car> synchronizedCars = carRepository.findAllById(ids);
            for (Car car : synchronizedCars) {
                car.setSynchronizedFlag(true);
            }
            carRepository.saveAll(synchronizedCars);
        }
    }

    @Override
    public void sendUsers(List<UserDTO> users) {
        List<UUID> ids = webClient.post()
                .uri("/sync/users")
                .bodyValue(users)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {})
                .doOnSuccess(response -> System.out.println("Users sent to headquarters."))
                .doOnError(error -> System.err.println("Error while sending users to headquarters: " + error.getMessage()))
                .block();

        if (ids != null && !ids.isEmpty()) {
            List<User> synchronizedUsers = userRepository.findAllById(ids);
            for (User user : synchronizedUsers) {
                user.setSynchronizedFlag(true);
            }
            userRepository.saveAll(synchronizedUsers);
        }
    }

    @Override
    public void sendOrders(List<OrderDTO> orders) {
        List<UUID> ids = webClient.post()
                .uri("/sync/orders")
                .bodyValue(orders)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UUID>>() {})
                .doOnSuccess(response -> System.out.println("Orders sent to headquarters."))
                .doOnError(error -> System.err.println("Error while sending orders to headquarters: " + error.getMessage()))
                .block();

        if (ids != null && !ids.isEmpty()) {
            List<Order> synchronizedOrders = orderRepository.findAllById(ids);
            for (Order order : synchronizedOrders) {
                order.setSynchronizedFlag(true);
            }
            orderRepository.saveAll(synchronizedOrders);
        }
    }

    @Scheduled(fixedDelay = 60 * 1000/*4 * 60 * 1000*/)
    public void synchronizeDataWithHeadquarters() {
        List<Car> cars = carRepository.findAllBySynchronizedFlag(false);
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            CarDTO dto = buildCarDTO(car);
            carDTOs.add(dto);
        }
        sendCars(carDTOs);

        List<Location> locations = locationRepository.findAllBySynchronizedFlag(false);
        List<LocationDTO> locationDTOs = new ArrayList<>();
        for (Location location : locations) {
            LocationDTO dto = new LocationDTO(
                    location.getId(),
                    location.getCar().getId(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getTime()
            );
            locationDTOs.add(dto);
        }
        sendLocations(locationDTOs);

        List<User> users = userRepository.findAllBySynchronizedFlag(false);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO dto = buildUserDTO(user);
            userDTOs.add(dto);
        }
        sendUsers(userDTOs);

        List<Order> orders = orderRepository.findAllBySynchronizedFlag(false);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order: orders) {
            OrderDTO dto = buildOrderDTO(order);
            orderDTOs.add(dto);
        }
        sendOrders(orderDTOs);
    }

    @Override
    public void getBranchId(String branchUrl) {
        BRANCH_ID = webClient.get()
                .uri("/branches/by-url?url=" + branchUrl)
                .retrieve()
                .bodyToMono(UUID.class)
                .doOnSuccess(response -> System.out.println("Obtained branch id: " + response))
                .doOnError(error -> System.err.println("Error while getting branch id: " + error.getMessage()))
                .block();
    }

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
                BRANCH_ID
        );
    }

    @Override
    public CarDTO buildCarDTO(Car car) {
        return new CarDTO(
            car.getId(),
            car.getCarStatus(),
            car.getMileage(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            null,
            null,
            null,
            0,
            null
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
