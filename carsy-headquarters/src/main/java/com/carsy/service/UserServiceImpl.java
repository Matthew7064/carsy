package com.carsy.service;

import com.carsy.dto.AddressDTO;
import com.carsy.dto.UserDTO;
import com.carsy.exception.InvalidDataException;
import com.carsy.model.Address;
import com.carsy.model.Branch;
import com.carsy.model.Role;
import com.carsy.model.User;
import com.carsy.repository.BranchRepository;
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
    private final BranchRepository branchRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
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
            updateBranches(user, foundUser);
            AddressUtils.editAddress(user.getAddress(), foundUser.getAddress());
            return userRepository.save(foundUser);
        }
        return null;
    }

    @Override
    public User updateUser(User user, UUID id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser != null) {
            if (user.getPesel() != null && !user.getPesel().isBlank()) foundUser.setPesel(user.getPesel());
            if (user.getName() != null && !user.getName().isBlank()) foundUser.setName(user.getName());
            if (user.getSurname() != null && !user.getSurname().isBlank()) foundUser.setSurname(user.getSurname());
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                if (!user.getEmail().matches(emailRegex)) {
                    throw new InvalidDataException("Invalid email format.");
                }
                User existingUser = userRepository.findByEmail(user.getEmail());
                if (existingUser != null && !existingUser.getId().equals(id)) {
                    throw new InvalidDataException("Email already exists.");
                }
                foundUser.setEmail(user.getEmail());
            }
            if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
                String phoneNumberRegex = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}";
                if (!user.getPhoneNumber().matches(phoneNumberRegex)) {
                    throw new InvalidDataException("Invalid phone number format.");
                }
                User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
                if (existingUser != null && !existingUser.getId().equals(id)) {
                    throw new InvalidDataException("Phone number already exists.");
                }
                foundUser.setPhoneNumber(user.getPhoneNumber());
            }
            if (user.getAccountNumber() != null && !user.getAccountNumber().isBlank()) {
                String accountNumberRegex = "\\d{26}";
                if (!user.getAccountNumber().matches(accountNumberRegex)) {
                    throw new InvalidDataException("Invalid account number format.");
                }
                User existingUser = userRepository.findByAccountNumber(user.getAccountNumber());
                if (existingUser != null && !existingUser.getId().equals(id)) {
                    throw new InvalidDataException("Account number already exists.");
                }
                foundUser.setAccountNumber(user.getAccountNumber());
            }
            if (user.getLogin() != null && !user.getLogin().isBlank()) {
                User existingUser = userRepository.findByLogin(user.getLogin());
                if (existingUser != null && !existingUser.getId().equals(id)) {
                    throw new InvalidDataException("Login already exists.");
                }
                foundUser.setLogin(user.getLogin());
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) foundUser.setPassword(user.getPassword());
            if (user.getRoles() != null) updateRoles(user, foundUser);
            if (user.getAddress() != null) AddressUtils.updateAddress(user.getAddress(), foundUser.getAddress());
            if (user.getBranches() != null) updateBranches(user, foundUser);
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

    public List<UUID> syncUsers(List<UserDTO> users) {
        List<UUID> ids = new ArrayList<>();
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
            user.setPassword("password");

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

            Branch branch = branchRepository.findById(dto.branchId()).orElse(null);
            if (branch != null) user.getBranches().add(branch);

            userRepository.save(user);
            ids.add(user.getId());
        }
        return ids;
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
