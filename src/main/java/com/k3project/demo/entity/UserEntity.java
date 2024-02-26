package com.k3project.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_k3",schema = "public")
public class UserEntity {
    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID userId;
    @Column(name = "first_name")
    @NotBlank(message = "First name should not be empty")
    @NotNull(message = "First name should not be empty")
    private String firstName;
    @Column(name ="last_name")
    @NotBlank(message = "Last name should not be empty")
    @NotNull(message = "Last name should not be empty")
    private String lastName;
    @Column(name = "address")
    @NotBlank(message = "Address should not be empty")
    @NotNull(message = "Address should not be empty")
    private String address;
    @Column(name = "phone_number")
    @NotBlank(message = "Phone number should not be empty")
    @NotNull(message = "Phone number not be empty")
    private String phoneNumber;
    @Column(name = "email")
    @NotBlank(message = "Email should not be empty")
    @NotNull(message = "Email should not be empty")
    private String email;
    //TODO add validation annotations
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role>roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public UserEntity(UUID userId, String firstName, String lastName, String address, String phoneNumber, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
