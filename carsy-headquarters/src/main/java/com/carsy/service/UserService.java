package com.carsy.service;

import com.carsy.dto.UserDTO;
import com.carsy.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void addUser(User user);
    User editUser(User user, UUID id);
    User updateUser(User user, UUID id);
    List<User> findAllUsers();
    void removeUser(UUID id);
    User findUser(UUID id);
    void syncUsers(List<UserDTO> users);
}
