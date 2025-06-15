package com.carsy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String role;

    public Role() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role roleObj = (Role) o;
        return role != null && role.equals(roleObj.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank String getRole() {
        return role;
    }

    public void setRole(@NotBlank String role) {
        this.role = role;
    }
}
