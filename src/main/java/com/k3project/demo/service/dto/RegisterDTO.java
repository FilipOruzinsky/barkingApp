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
}
