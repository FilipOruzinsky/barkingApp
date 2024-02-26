package com.k3project.demo.repository;

import com.k3project.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//TODO Integer here ? not UUID ?
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role>findByName(String name);
}
