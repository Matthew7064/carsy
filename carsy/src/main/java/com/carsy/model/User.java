package com.carsy.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits")
    private String pesel;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String surname;

    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX")
    private String phoneNumber;

    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits")
    private String accountNumber;

    @NotNull
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != 0 && id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotNull @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits") String getPesel() {
        return pesel;
    }

    public void setPesel(@NotNull @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits") String pesel) {
        this.pesel = pesel;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    public void setSurname(@NotNull String surname) {
        this.surname = surname;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull @Pattern(regexp = "\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}", message = "Phone number must have format: +XX-XXX-XXX-XXX") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotNull @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits") String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(@NotNull @Pattern(regexp = "\\d{26}", message = "Account number must have 26 digits") String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public @NotNull String getLogin() {
        return login;
    }

    public void setLogin(@NotNull String login) {
        this.login = login;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
