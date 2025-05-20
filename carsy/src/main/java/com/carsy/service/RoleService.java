package com.carsy.service;

import com.carsy.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    Role editRole(Role role, long id);
    List<Role> findAllRoles();
    void removeRole(long id);
    Role findRole(long id);
}
