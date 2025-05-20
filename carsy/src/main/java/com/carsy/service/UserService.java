package com.carsy.service;

import com.carsy.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    User editUser(User user, long id);
    User updateUser(User user, long id);
    List<User> findAllUsers();
    void removeUser(long id);
    User findUser(long id);
}
