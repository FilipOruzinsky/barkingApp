package com.k3project.demo.dto;

import java.util.UUID;

public record UserDTO (
      UUID userId,
      String firstName,
      String lastName,
      String address,
      String phoneNumber,
      String email


) {
}

