package com.carsy.service;

import com.carsy.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void addRole(Role role);
    Role editRole(Role role, UUID id);
    List<Role> findAllRoles();
    void removeRole(UUID id);
    Role findRole(UUID id);
}
