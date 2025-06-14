package com.carsy.service;

import com.carsy.model.Branch;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import com.carsy.repository.BranchRepository;
import com.carsy.repository.CarRepository;
import com.carsy.repository.UserRepository;
import com.carsy.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, UserRepository userRepository, CarRepository carRepository) {
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void addBranch(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public Branch editBranch(Branch branch, UUID id) {
        Branch foundBranch = branchRepository.findById(id).orElse(null);
        if (foundBranch != null) {
            AddressUtils.updateAddress(branch.getAddress(), foundBranch.getAddress());
            updateUsers(branch, foundBranch);
            updateCars(branch, foundBranch);
            return branchRepository.save(branch);
        }
        return null;
    }

    @Override
    public Branch updateBranch(Branch branch, UUID id) {
        Branch foundBranch = branchRepository.findById(id).orElse(null);
        if (foundBranch != null) {
            if (branch.getAddress() != null) AddressUtils.updateAddress(branch.getAddress(), foundBranch.getAddress());
            if (branch.getUsers() != null) updateUsers(branch, foundBranch);
            if (branch.getCars() != null) updateCars(branch, foundBranch);
            return branchRepository.save(branch);
        }
        return null;
    }

    @Override
    public List<Branch> findAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public void removeBranch(UUID id) {
        branchRepository.deleteById(id);
    }

    @Override
    public Branch findBranch(UUID id) {
        return branchRepository.findById(id).orElse(null);
    }

    private void updateUsers(Branch branch, Branch foundBranch) {
        Set<User> attachedUsers = new HashSet<>();
        for (User user : branch.getUsers()) {
            User attachedUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found, id: " + user.getId()));
            attachedUser.getBranches().add(foundBranch);
            attachedUsers.add(attachedUser);
        }
        foundBranch.setUsers(attachedUsers);
    }

    private void updateCars(Branch branch, Branch foundBranch) {
        Set<Car> attachedCars = new HashSet<>();
        for (Car car : branch.getCars()) {
            Car attachedCar = carRepository.findById(car.getId()).orElseThrow(() -> new IllegalArgumentException("Car not found, id: " + car.getId()));
            attachedCar.setBranch(foundBranch);
            attachedCars.add(attachedCar);
        }
        foundBranch.setCars(attachedCars);
    }
}
