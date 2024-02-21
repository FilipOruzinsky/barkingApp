package com.k3project.demo.controller;

import com.k3project.demo.service.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Tag(name = "User Management", description = "APIs for managing users")
@RestController
//define that all methods will returned Body in serialized Json format
@RequestMapping(path = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users from the database.
     * <p>
     * {@code GET/users} : get all users from database
     * This method delegates to {@link UserService#findAllUsers()} to fetch the users.
     *
     * @return A list of {@code UserDTO} objects representing all users.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if list of users is not found in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred,
     * @see UserService#findAllUsers()
     */
    @GetMapping(value = "users")
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Retrieves a user by their email address.
     * <p>
     * {@code GET /email}: get User from database regarding to his email address
     *
     * @param email The email address of the user to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} and the UserDTO is returned in Json body.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if email does not match with any email in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#findUserByEmail(String)
     */
    @GetMapping("/email")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        Optional<UserDTO> result = userService.findUserByEmail(email);
        return result
                .map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a user by their first name .
     * <p>
     * {@code GET /firstName}: get User from database regarding to his first name
     *
     * @param firstName The firstName  of the user to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} and the UserDTO is returned in Json body.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if first name does not match with any email in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#findUserByfirstName(String)
     */
    @GetMapping(value = "/firstName")
    public ResponseEntity<UserDTO> findByfirstName(@RequestParam String firstName) {
        Optional<UserDTO> result = userService.findUserByfirstName(firstName);
        return result.map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a user by their first and last name  address.
     * <p>
     * {@code GET /findUserByfullName}: get User from database regarding to his first name
     *
     * @param firstName The first name of the user to retrieve.
     * @param lastName  The last name of the user to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} and the UserDTO is returned in Json body.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if first name  nad last name does not match with any email in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#findByFirstNameAndLastName(String, String)
     */
    @GetMapping("/findUserByfullName")
    public ResponseEntity<UserDTO> findByfirstNameAndlastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<UserDTO> result = userService.findByFirstNameAndLastName(firstName, lastName);
        return result.map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());

    }

    /**
     * Saves a new user to database .
     * <p>
     * This endpoint allows the client to create a new user by providing a valid UserDTO object in the request body.
     * The user data is validated using Bean Validation annotations.
     * {@code POST/postUser}: save new User to database
     *
     * @param userDTO The UserDTO object containing the user information to be saved.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} user was successfully saved in database
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#saveUser(UserDTO)
     */
    @PostMapping(value = "/postUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result = userService.saveUser(userDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * Updates an existing user.
     * <p>
     * This endpoint allows the client to update an existing user by providing a valid UserDTO object in the request body.
     * The user data is validated using Bean Validation annotations.
     * {@code PUT/putToUser} : update existing user
     *
     * @param userDTO The UserDTO object containing the updated user information.
     * @return the {@link ResponseEntity} with status {@code 200(OK) user was successfully updated}.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if user which should be updated is not exist in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#updateUser(UserDTO)
     */
    @PutMapping(value = "/putToUser")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userDTO2 = userService.updateUser(userDTO);
        return ResponseEntity.ok()
                .body(userDTO2);
    }

    /**
     * Deletes a user by their unique/Id identifier.
     * <p>
     * This endpoint allows the client to delete a user by providing their unique identifier.
     * {@code DELETE/deleteUser}: user in database will be delete
     *
     * @param userId The unique identifier of the user to be deleted.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if user with requested Id does not exist in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#deleteUser(UUID)
     */
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


