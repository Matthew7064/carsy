package com.carsy.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits")
    private String pesel;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String surname;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX")
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits")
    private String accountNumber;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>(2);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    @Valid
    private Address address;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Branch> branches = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits") String getPesel() {
        return pesel;
    }

    public void setPesel(@NotBlank @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits") String pesel) {
        this.pesel = pesel;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getSurname() {
        return surname;
    }

    public void setSurname(@NotBlank String surname) {
        this.surname = surname;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits") String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(@NotBlank @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits") String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public @NotBlank String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank String login) {
        this.login = login;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public @Valid Address getAddress() {
        return address;
    }

    public void setAddress(@Valid Address address) {
        this.address = address;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branch> branches) {
        this.branches = branches;
    }
}
