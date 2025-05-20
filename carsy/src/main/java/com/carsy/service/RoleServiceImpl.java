package com.carsy.service;

import com.carsy.model.Role;
import com.carsy.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role editRole(Role role, long id) {
        Role foundRole = roleRepository.findById(id).orElse(null);
        if (foundRole != null) {
            foundRole.setRole(role.getRole());
            return roleRepository.save(foundRole);
        }
        return null;
    }

    @Override
    @Transactional
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void removeRole(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role findRole(long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }
}
