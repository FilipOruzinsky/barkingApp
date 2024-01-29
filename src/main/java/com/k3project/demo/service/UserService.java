package com.k3project.demo.service;

import com.k3project.demo.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.mapper.UserDTOMapper;
import com.k3project.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService  {


    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }



    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);

    }
}
