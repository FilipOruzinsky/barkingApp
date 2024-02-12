package com.k3project.demo.controller;

import com.k3project.demo.service.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@RestController
//define that all methods will returned Body in serialized Json format
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers")
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/email")
    public Optional<UserDTO> findUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping(value = "/firstName")
    public Optional<UserDTO> findByfirstName(@RequestParam String firstName) {
        return userService.findUserByfirstName(firstName);
    }

    @GetMapping("/findUserByfullName")
    public Optional<UserDTO> findByfirstNameAndlastName(@RequestParam String firstName, @RequestParam String lastName) {
        System.out.println("tusom" + " " + userService.findByfirstNameAndLastName(firstName, lastName));
        return userService.findByfirstNameAndlastName(firstName, lastName);

    }

    //ked som manualne zadal ID usera nefungoval post request potom mi napadlo ze UUID generuje id
    @PostMapping(value = "/postUser")
    public User saveUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }


    //bez value nefungoval putrequest
    @PutMapping(value = "/putToUser")
    public Optional<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        //???
        return Optional.ofNullable(userService.updateUser(userDTO));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@RequestParam("userId") UUID userId) {
        userService.deleteUser(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String fieldName = fieldError.getField();
        String errorMessage = fieldError.getDefaultMessage();
        return fieldName + ": " + errorMessage;
    }


    }

