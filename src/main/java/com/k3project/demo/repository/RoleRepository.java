package com.k3project.demo.repository;

import com.k3project.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
//TODO Integer here ? not UUID ?
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role>findByName(String name);
}
