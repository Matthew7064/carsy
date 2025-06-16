package com.carsy.controller;

import com.carsy.dto.CarDTO;
import com.carsy.dto.LocationDTO;
import com.carsy.dto.OrderDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Branch;
import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import com.carsy.repository.BranchRepository;
import com.carsy.repository.CarRepository;
import com.carsy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

//for internal communication between headquarters and branches
@RestController
@RequestMapping("/sync")
public class SyncController {
    private final LocationService locationService;
    private final UserService userService;
    private final OrderService orderService;
    private final CarService carService;
    private final SyncService syncService;
    private final BranchRepository branchRepository;
    private final CarRepository carRepository;

    @Autowired
    public SyncController(LocationService locationService, UserService userService, OrderService orderService, CarService carService, SyncService syncService, BranchRepository branchRepository, CarRepository carRepository) {
        this.locationService = locationService;
        this.userService = userService;
        this.orderService = orderService;
        this.carService = carService;
        this.syncService = syncService;
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
    }

    @PostMapping("/locations")
    public ResponseEntity<List<UUID>> syncLocations(@RequestBody List<LocationDTO> locations) {
        List<UUID> ids = locationService.syncLocations(locations);
        return ResponseEntity.ok(ids);
    }

    @PostMapping("/users")
    public ResponseEntity<List<UUID>> syncUsers(@RequestBody List<UserDTO> users) {
        List<UUID> ids = userService.syncUsers(users);
        return ResponseEntity.ok(ids);
    }

    @PostMapping("/orders")
    public ResponseEntity<List<UUID>> syncOrders(@RequestBody List<OrderDTO> orders) {
        List<UUID> ids = orderService.syncOrders(orders);
        return ResponseEntity.ok(ids);
    }

    @PostMapping("/cars")
    public ResponseEntity<List<UUID>> syncCars(@RequestBody List<CarDTO> cars) {
        List<UUID> ids = carService.syncCars(cars);
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/branches/{branchId}/users/{userId}")
    public ResponseEntity<UserDTO> getUserFromBranch(@PathVariable("branchId") UUID branchId,
                                                     @PathVariable("userId") UUID userId) {
        User user = userService.findUser(userId);
        if (user != null) return ResponseEntity.ok(syncService.buildUserDTO(user));
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (branch != null) {
            String url = branch.getUrl();
            try {
                UserDTO userDTO = WebClient.create()
                        .get()
                        .uri(url + "sync/users/" + userId)
                        .retrieve()
                        .bodyToMono(UserDTO.class)
                        .block();
                if (userDTO != null) {
                    return ResponseEntity.ok(userDTO);
                }
            } catch (Exception ex) {
                System.err.println("Error while calling branch: " + ex.getMessage());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/branches/{branchId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderFromBranch(@PathVariable("branchId") UUID branchId,
                                                     @PathVariable("orderId") UUID orderId) {
        Order order = orderService.findOrder(orderId);
        if (order != null) return ResponseEntity.ok(syncService.buildOrderDTO(order));
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (branch != null) {
            String url = branch.getUrl();
            try {
                OrderDTO orderDTO = WebClient.create()
                        .get()
                        .uri(url + "sync/orders/" + orderId)
                        .retrieve()
                        .bodyToMono(OrderDTO.class)
                        .block();
                if (orderDTO != null) {
                    return ResponseEntity.ok(orderDTO);
                }
            } catch (Exception ex) {
                System.err.println("Error while calling branch: " + ex.getMessage());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/branches/{branchId}/cars/{carId}")
    public ResponseEntity<CarDTO> transferCarToBranch(@PathVariable("branchId") UUID branchId,
                                                         @PathVariable("carId") UUID carId) {
        Car car = carService.findCar(carId);
        if (car != null) {
            Branch branch = branchRepository.findById(branchId).orElse(null);
            if (branch != null) {
                String url = branch.getUrl();
                try {
                    WebClient.create()
                            .get()
                            .uri(url + "sync/cars/" + carId + "/delete")
                            .retrieve()
                            .toBodilessEntity()
                            .subscribe();
                } catch (Exception ex) {
                    System.err.println("Error while deleting car: " + ex.getMessage());
                }
            }
            car.setBranch(branch);
            carRepository.save(car);
            return ResponseEntity.ok(syncService.buildCarDTO(car));
        }
        return ResponseEntity.notFound().build();
    }
}
