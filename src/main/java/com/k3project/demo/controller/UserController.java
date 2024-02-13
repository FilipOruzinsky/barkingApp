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
        Optional<UserDTO> userOptional = userService.findUserByEmail(email);
        return userOptional
                .map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/firstName")
    public ResponseEntity<UserDTO> findByfirstName(@RequestParam String firstName) {
        Optional<UserDTO> userDTOOptional = userService.findUserByfirstName(firstName);
        return userDTOOptional.map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findUserByfullName")
    public Optional<UserDTO> findByfirstNameAndlastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<UserDTO> userDTO3 = userService.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok()
                .body(userDTO3).getBody();

    }

    //ked som manualne zadal ID usera nefungoval post request potom mi napadlo ze UUID generuje id
    //TODO ResponseEn.
    @PostMapping(value = "/postUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userDTO4 =  userService.saveUser(userDTO);
        return ResponseEntity.ok()
                .body(userDTO4);
    }

    //bez value nefungoval putrequest
    @PutMapping(value = "/putToUser")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userDTO2 = userService.updateUser(userDTO);
        return ResponseEntity.ok()
                .body(userDTO2);
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


