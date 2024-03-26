package com.k3project.demo.controller;

import com.k3project.demo.entity.Role;
import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.repository.RoleRepository;
import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.security.JWTGenerator;
import com.k3project.demo.service.UserService;
import com.k3project.demo.service.dto.AuthResponseDTO;
import com.k3project.demo.service.dto.LoginDTO;
import com.k3project.demo.service.dto.RegisterDTO;
import com.k3project.demo.service.dto.UserEntityDTO;
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
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }
    /**
     * Registers a new user.
     * <p>
     * {@code POST /register}: Registers a new user with the provided details.
     *
     * @param registerDTO The registration details of the user.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} and a success message if registration is successful,
     * or with status {@code 400(Bad Request)} if the provided email is already taken,
     * or with status {@code 500(Internal Server Error)} if an unexpected error occurs during registration.
     * @throws RuntimeException if the role "USER" is not found in the database.
     * @see UserRepository#findByEmail(String)
     * @see RoleRepository#findByName(String)
     * @see UserService#saveUser(UserEntityDTO)
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
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

        UserEntity registeredUser = userRepository.save(userEntity);

        logger.info("User registered successfully: {}", registeredUser);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

    }
    /**
     * Authenticates a user.
     * <p>
     * {@code POST /login}: Authenticates a user with the provided login credentials.
     *
     * @param loginDTO The login credentials of the user.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} and a JWT token in the body if authentication is successful,
     * or with status {@code 401(Unauthorized)} if authentication fails.
     * @see AuthenticationManager#authenticate(Authentication)
     * @see SecurityContextHolder#getContext()
     * @see JWTGenerator#generateToken(Authentication)
     */
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getFirstName(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        logger.info("User '{}' authenticated successfully. JWT token generated.", loginDTO.getFirstName());
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
