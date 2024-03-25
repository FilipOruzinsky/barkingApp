package com.k3project.demo.service;

import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.service.dto.UserEntityDTO;
import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    public List<UserEntityDTO> findAllUsers() {
        logger.info("Method findAllUsers start");
        try {
            List<UserEntityDTO> users = userRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
            logger.info("Retrieved {} users from database", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error occurred in findAllUsers method: {}", e.getMessage(), e);
            throw e;
        }finally {
            logger.info("Method findAllUsers end");

        }
    }

    public Optional<UserEntityDTO> findUserByFirstName(String firstName) {
        logger.info("Method findByFirstName start.Searching for user with first name : {}",firstName);
        try {Optional<UserEntityDTO> userOptional = userRepository.findByFirstName(firstName).map(userMapper::toDto);
            if (userOptional.isPresent()){
                logger.info("User with first name '{}' found ",firstName);
            }else {
                logger.info("User with first name '{}' not found ",firstName);
            }
            return userOptional;
        }catch (Exception e){
            logger.error("Error occurred in findUserByFirstName method: {}", e.getMessage(), e);
            throw e;
        }finally {
            logger.info("Method findUserByFirstName end");

        }
    }

    public Optional<UserEntityDTO> findUserByEmail(String email) {
        logger.info("Method findUserByEmail start. Searching for user with email: {}", email);
        try {
            Optional<UserEntityDTO> userOptional = userRepository.findByEmail(email)
                    .map(userMapper::toDto);
            if (userOptional.isPresent()) {
                logger.info("User with email '{}' found", email);
            } else {
                logger.info("User with email '{}' not found", email);
            }
            return userOptional;
        } catch (Exception e) {
            logger.error("Error occurred in findUserByEmail method: {}", e.getMessage(), e);
            throw e;
        } finally {
            logger.info("Method findUserByEmail end");
        }
    }

    public Optional<UserEntityDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        logger.info("Method findByFirstNameAndLastName start. Searching for user with firstName: {}, lastName: {}", firstName, lastName);
        try {
            Optional<UserEntity> userOptional = userRepository.findByFirstNameAndLastName(firstName, lastName);
            if (userOptional.isPresent()) {
                logger.info("User with firstName '{}' and lastName '{}' found", firstName, lastName);
                return Optional.ofNullable(userMapper.toDto(userOptional.get()));
            } else {
                logger.info("User not found for firstName: {}, lastName: {}", firstName, lastName);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error occurred in findByFirstNameAndLastName method: {}", e.getMessage(), e);
            throw e;
        }finally {
            logger.info("Method findByFirstNameAndLastName end ");
        }
    }


    public UserEntityDTO saveUser(UserEntityDTO userEntityDTO) {
        logger.info("Method saveUser start. Saving user: {}", userEntityDTO);
        try {
            UserEntity user1 = userMapper.toEntity(userEntityDTO);
            UserEntity savedUser = userRepository.save(user1);
            logger.info("User saved successfully: {}", savedUser);
            return userMapper.toDto(savedUser);
        } catch (Exception e) {
            logger.error("Error occurred while saving user: {}", e.getMessage(), e);
            throw e;
        } finally {
            logger.info("Method saveUser end");
        }
    }

    public UserEntityDTO updateUser(UserEntityDTO userEntityDTO) {
        logger.info("Method updateUser start. Updating user: {}", userEntityDTO);
        try {
            UserEntity user = userMapper.toEntity(userEntityDTO);
            UserEntity updatedUser = userRepository.save(user);
            logger.info("User updated successfully: {}", updatedUser);
            return userMapper.toDto(updatedUser);
        } catch (Exception e) {
            logger.error("Error occurred while updating user: {}", e.getMessage(), e);
            throw e;
        } finally {
            logger.info("Method updateUser end");
        }
    }

    public void deleteUser(UUID userId) {
        logger.info("Method deleteUser start. Deleting user with ID: {}", userId);
        try {
            userRepository.deleteById(userId);
            logger.info("User with ID '{}' deleted successfully", userId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user with ID '{}': {}", userId, e.getMessage(), e);
            throw e;
        } finally {
            logger.info("Method deleteUser end");
        }
    }
}









