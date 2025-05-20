package com.carsy.controller;

import com.carsy.model.Role;
import com.carsy.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id) {
        Role role = roleService.findRole(id);
        if (role != null) return ResponseEntity.ok(role);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody @Valid Role role) {
        roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> editRole(@PathVariable("id") Long id, @RequestBody @Valid Role role) {
        Role updatedRole = roleService.editRole(role, id);
        if (updatedRole != null) return ResponseEntity.ok(updatedRole);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRole(@PathVariable("id") Long id) {
        roleService.removeRole(id);
        return ResponseEntity.noContent().build();
    }
}
