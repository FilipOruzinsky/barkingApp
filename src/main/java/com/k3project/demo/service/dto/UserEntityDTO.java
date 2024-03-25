package com.k3project.demo.service.dto;

import java.util.UUID;

public record UserEntityDTO(
      UUID userId,
      String firstName,
      String lastName,
      String address,
      String phoneNumber,
      String email
){}

