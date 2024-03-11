package com.k3project.demo.controller;

import com.k3project.demo.entity.Role;
import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.repository.RoleRepository;
import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.service.dto.LoginDTO;
import com.k3project.demo.service.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if(userRepository.existByEmail(registerDTO.getFirstName())){
            return new ResponseEntity<>("Email is taken !", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerDTO.getFirstName());
        userEntity.setLastName(registerDTO.getLastName());
        userEntity.setAddress(registerDTO.getAddress());
        userEntity.setPhoneNumber(registerDTO.getPhoneNumber());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(registerDTO.getPassword());

        userEntity.setPassword(passwordEncoder.encode((registerDTO.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        userEntity.setRoles(Collections.singletonList(roles));

        userRepository.save(userEntity);

        return new ResponseEntity<>("User registered succes!",HttpStatus.OK);

    }
    @PostMapping("login")
    public ResponseEntity<String>login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getFirstName(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed success!",HttpStatus.OK);
    }
}
