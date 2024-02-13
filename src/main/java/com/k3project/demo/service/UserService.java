package com.k3project.demo.service;

import com.k3project.demo.service.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findUserByfirstName(String firstName) {
        return userRepository.findByfirstName(firstName).map(userMapper::toDto);
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    public Optional<UserDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        Optional<User> userOptional = userRepository.findByFirstNameAndLastName(firstName, lastName);
        if (userOptional.isPresent()) {
            return Optional.ofNullable(userMapper.toDto(userOptional.get()));
        } else {
            System.out.println("User not found for firstName: " + firstName + ", lastName: " + lastName);
            return null;
        }
    }


    public UserDTO saveUser(UserDTO userDTO) {
        User user1 = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user1);
        return userMapper.toDto(savedUser);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);

    }
}









