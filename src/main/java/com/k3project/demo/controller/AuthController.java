package com.k3project.demo.controller;

import com.k3project.demo.entity.Role;
import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.repository.RoleRepository;
import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.security.JWTGenerator;
import com.k3project.demo.service.dto.AuthResponseDTO;
import com.k3project.demo.service.dto.LoginDTO;
import com.k3project.demo.service.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder,JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        logger.info("Method register start. Registering new user with email: {}", registerDTO.getEmail());
        try {
            if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
                logger.warn("Email '{}' is already taken", registerDTO.getEmail());
                return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(registerDTO.getFirstName());
            userEntity.setLastName(registerDTO.getLastName());
            userEntity.setAddress(registerDTO.getAddress());
            userEntity.setPhoneNumber(registerDTO.getPhoneNumber());
            userEntity.setEmail(registerDTO.getEmail());

            userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

            Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
            userEntity.setRoles(Collections.singletonList(role));

            UserEntity registeredUser =userRepository.save(userEntity);

            logger.info("User registered successfully: {}",registeredUser);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while registering user: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to register user!", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Method register end");
        }
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        logger.info("Method login start. Attempting to authenticate user: {}", loginDTO.getFirstName());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getFirstName(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            logger.info("User '{}' authenticated successfully. JWT token generated.", loginDTO.getFirstName());
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user '{}': {}", loginDTO.getFirstName(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error occurred during login process: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Method login end");
        }
    }
}
