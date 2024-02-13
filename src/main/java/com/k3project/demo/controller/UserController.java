package com.k3project.demo.controller;

import com.k3project.demo.service.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        Optional<UserDTO> result = userService.findUserByEmail(email);
        return result
                .map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/firstName")
    public ResponseEntity<UserDTO> findByfirstName(@RequestParam String firstName) {
        Optional<UserDTO> result = userService.findUserByfirstName(firstName);
        return result.map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findUserByfullName")
    public ResponseEntity<UserDTO> findByfirstNameAndlastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<UserDTO> result = userService.findByFirstNameAndLastName(firstName, lastName);
        return result.map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());

    }

    //ked som manualne zadal ID usera nefungoval post request potom mi napadlo ze UUID generuje id
    @PostMapping(value = "/postUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result =  userService.saveUser(userDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    //bez value nefungoval putrequest
    @PutMapping(value = "/putToUser")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result = userService.updateUser(userDTO);
        return ResponseEntity.ok()
                .body(result);
    }


    @DeleteMapping("/deleteUser")
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


