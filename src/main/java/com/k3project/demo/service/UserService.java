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
//    public Optional<UserDTO> findById(UUID userId) {
//        return userRepository.findById(userId).map(userMapper::toDto);
//    }

    public Optional<UserDTO> findUserByEmail(String email) {
        System.out.println("findUserByemail: " + userRepository.findByEmail(email).map(userMapper::toDto) );
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }
    public Optional<UserDTO> findUserByfirstName(String firstName){
        System.out.println("jadaaaa" + userRepository.findByfirstName(firstName).map(userMapper::toDto));
        return userRepository.findByfirstName(firstName).map(userMapper::toDto);
    }

    //    public User findUserByMail (String email){return userRepository.findByMail(email);}
    public User saveUser(User user) {
        return userRepository.save(user);
    }

//    public UserDTO updateUser(User user) {
//        return userRepository.saver(user);
//    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);

    }
}
