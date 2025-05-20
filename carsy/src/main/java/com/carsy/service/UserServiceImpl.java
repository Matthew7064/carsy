package com.carsy.service;

import com.carsy.model.Branch;
import com.carsy.model.Role;
import com.carsy.model.User;
import com.carsy.repository.BranchRepository;
import com.carsy.repository.RoleRepository;
import com.carsy.repository.UserRepository;
import com.carsy.utils.AddressUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User editUser(User user, long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser != null) {
            foundUser.setPesel(user.getPesel());
            foundUser.setName(user.getName());
            foundUser.setSurname(user.getSurname());
            foundUser.setEmail(user.getEmail());
            foundUser.setPhoneNumber(user.getPhoneNumber());
            foundUser.setAccountNumber(user.getAccountNumber());
            foundUser.setLogin(user.getLogin());
            foundUser.setPassword(user.getPassword());
            updateRoles(user, foundUser);
            updateBranches(user, foundUser);
            AddressUtils.updateAddress(user.getAddress(), foundUser.getAddress());
            return userRepository.save(foundUser);
        }
        return null;
    }

    @Override
    @Transactional
    public User updateUser(User user, long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser != null) {
            if (user.getPesel() != null) foundUser.setPesel(user.getPesel());
            if (user.getName() != null) foundUser.setName(user.getName());
            if (user.getSurname() != null) foundUser.setSurname(user.getSurname());
            if (user.getEmail() != null) foundUser.setEmail(user.getEmail());
            if (user.getPhoneNumber() != null) foundUser.setPhoneNumber(user.getPhoneNumber());
            if (user.getAccountNumber() != null) foundUser.setAccountNumber(user.getAccountNumber());
            if (user.getLogin() != null) foundUser.setLogin(user.getLogin());
            if (user.getPassword() != null) foundUser.setPassword(user.getPassword());
            if (user.getRoles() != null) updateRoles(user, foundUser);
            if (user.getBranches() != null) updateBranches(user, foundUser);
            if (user.getAddress() != null) AddressUtils.updateAddress(user.getAddress(), foundUser.getAddress());
            return userRepository.save(foundUser);
        }
        return null;
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUser(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    private void updateRoles(User user, User foundUser) {
        Set<Role> attachedRoles = new HashSet<>();
        for (Role role: user.getRoles()) {
            Role attachedRole = roleRepository.findById(role.getId()).orElseThrow(() -> new IllegalArgumentException("Role not found, id: " + role.getId()));
            attachedRoles.add(attachedRole);
        }
        foundUser.setRoles(attachedRoles);
    }

    private void updateBranches(User user, User foundUser) {
        Set<Branch> attachedBranches = new HashSet<>();
        for (Branch branch: user.getBranches()) {
            Branch attachedBranch = branchRepository.findById(branch.getId()).orElseThrow(() -> new IllegalArgumentException("Branch not found, id: " + branch.getId()));
            attachedBranch.getUsers().add(foundUser);
            attachedBranches.add(attachedBranch);
        }
        foundUser.setBranches(attachedBranches);
    }
}
