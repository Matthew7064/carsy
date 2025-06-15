package com.carsy.service;

import com.carsy.dto.AddressDTO;
import com.carsy.dto.UserDTO;
import com.carsy.model.Address;
import com.carsy.model.Role;
import com.carsy.model.User;
import com.carsy.repository.RoleRepository;
import com.carsy.repository.UserRepository;
import com.carsy.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User editUser(User user, UUID id) {
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
            AddressUtils.updateAddress(user.getAddress(), foundUser.getAddress());
            return userRepository.save(foundUser);
        }
        return null;
    }

    @Override
    public User updateUser(User user, UUID id) {
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
            if (user.getAddress() != null) AddressUtils.updateAddress(user.getAddress(), foundUser.getAddress());
            return userRepository.save(foundUser);
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void syncUsers(List<UserDTO> users) {
        for (UserDTO dto : users) {
            User user = userRepository.findById(dto.id()).orElse(new User());
            user.setId(dto.id());
            user.setPesel(dto.pesel());
            user.setName(dto.name());
            user.setSurname(dto.surname());
            user.setEmail(dto.email());
            user.setPhoneNumber(dto.phoneNumber());
            user.setAccountNumber(dto.accountNumber());
            user.setLogin(dto.login());

            Set<Role> roles = new HashSet<>();
            for (String roleName : dto.roles()) {
                Role role = roleRepository.findByRole(roleName);
                if (role == null) {
                    role = new Role();
                    role.setRole(roleName);
                    role = roleRepository.save(role);
                }
                roles.add(role);
            }
            user.setRoles(roles);

            Address address = user.getAddress();
            if (address == null) address = new Address();
            AddressDTO addressDto = dto.address();
            address.setId(addressDto.id());
            address.setStreet(addressDto.street());
            address.setCity(addressDto.city());
            address.setPostalCode(addressDto.postalCode());
            address.setCountry(addressDto.country());
            address.setNumber(addressDto.number());
            if (addressDto.flatNumber() != null && !addressDto.flatNumber().trim().isBlank()) address.setFlatNumber(addressDto.flatNumber());
            else address.setFlatNumber(null);
            user.setAddress(address);

            userRepository.save(user);
        }
    }

    private void updateRoles(User user, User foundUser) {
        Set<Role> attachedRoles = new HashSet<>();
        for (Role role: user.getRoles()) {
            Role attachedRole = roleRepository.findById(role.getId()).orElseThrow(() -> new IllegalArgumentException("Role not found, id: " + role.getId()));
            attachedRoles.add(attachedRole);
        }
        foundUser.setRoles(attachedRoles);
    }
}
