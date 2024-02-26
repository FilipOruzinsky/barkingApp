package com.k3project.demo.service.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class RegisterDTO {
    UUID userId;
    String firstName;
    String lastName;
    String password;
    String address;
    String phoneNumber;
    String email;

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
