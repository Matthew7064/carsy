package com.carsy.service;

import com.carsy.model.Role;
import com.carsy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role editRole(Role role, UUID id) {
        Role foundRole = roleRepository.findById(id).orElse(null);
        if (foundRole != null) {
            foundRole.setRole(role.getRole());
            return roleRepository.save(foundRole);
        }
        return null;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void removeRole(UUID id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findRole(UUID id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }
}
