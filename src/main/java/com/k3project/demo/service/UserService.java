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
        List<UserEntityDTO> users = userRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
            logger.debug("Retrieved {} users from database", users.size());
            return users;
    }

    public Optional<UserEntityDTO> findUserByFirstName(String firstName) {
        Optional<UserEntityDTO> userOptional = userRepository.findByFirstName(firstName).map(userMapper::toDto);
            if (userOptional.isPresent()){
                logger.debug("User with first name '{}' found ",firstName);
            }else {
                logger.debug("User with first name '{}' not found ",firstName);
            }
            return userOptional;
    }

    public Optional<UserEntityDTO> findUserByEmail(String email) {
            Optional<UserEntityDTO> userOptional = userRepository.findByEmail(email)
                    .map(userMapper::toDto);
            if (userOptional.isPresent()) {
                logger.debug("User with email '{}' found", email);
            } else {
                logger.debug("User with email '{}' not found", email);
            }
            return userOptional;
    }

    public Optional<UserEntityDTO> findByFirstNameAndLastName(String firstName, String lastName) {

            Optional<UserEntity> userOptional = userRepository.findByFirstNameAndLastName(firstName, lastName);
            if (userOptional.isPresent()) {
                logger.debug("User with firstName '{}' and lastName '{}' found", firstName, lastName);
                return Optional.ofNullable(userMapper.toDto(userOptional.get()));
            } else {
                logger.debug("User not found for firstName: {}, lastName: {}", firstName, lastName);
                return Optional.empty();
            }
    }


    public UserEntityDTO saveUser(UserEntityDTO userEntityDTO) {
            UserEntity user1 = userMapper.toEntity(userEntityDTO);
            UserEntity savedUser = userRepository.save(user1);
            logger.debug("User saved successfully: {}", savedUser);
            return userMapper.toDto(savedUser);
    }

    public UserEntityDTO updateUser(UserEntityDTO userEntityDTO) {
            UserEntity user = userMapper.toEntity(userEntityDTO);
            UserEntity updatedUser = userRepository.save(user);
            logger.debug("User updated successfully: {}", updatedUser);
            return userMapper.toDto(updatedUser);
    }

    public void deleteUser(UUID userId) {
            userRepository.deleteById(userId);
            logger.debug("User with ID '{}' deleted successfully", userId);

    }
}









