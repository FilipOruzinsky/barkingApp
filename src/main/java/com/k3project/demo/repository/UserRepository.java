package com.k3project.demo.repository;

import com.k3project.demo.entity.User;
import com.k3project.demo.service.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    //TODO pozriet spravny zapis query
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u WHERE u.firstName =:firstName" )
    Optional<User> findByfirstName(@Param("firstName")String firstName);
    @Query("SELECT u FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    Optional<User> findByfirstNameAndLastName(@Param("firstName")String firstName, @Param("lastName")String lastName);
}
