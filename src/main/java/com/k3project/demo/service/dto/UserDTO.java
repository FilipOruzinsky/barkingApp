package com.k3project.demo.service.dto;

import java.util.UUID;

public record UserDTO (
      UUID userId,
      String firstName,
      String lastName,
      String address,
      String phoneNumber,
      String email


) {
    @Override
    public UUID userId() {
        return userId;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
    }

    @Override
    public String email() {
        return email;
    }

}

