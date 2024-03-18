package com.k3project.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
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
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role>roles = new ArrayList<>();

}
