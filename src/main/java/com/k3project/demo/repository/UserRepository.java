package com.k3project.demo.repository;

import com.k3project.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(@Param("email") String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email = :email")

    Boolean existByEmail(@Param("email") String email);
    Optional<UserEntity> findByfirstName(@Param("firstName") String firstName);

    Optional<UserEntity> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
