package com.k3project.demo.controller;

import com.k3project.demo.service.dto.UserEntityDTO;
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
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/users")
    public List<UserEntityDTO> findAllUsers() {
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
    @GetMapping("/user/email")
    public ResponseEntity<UserEntityDTO> findUserByEmail(@RequestParam String email) {
        Optional<UserEntityDTO> result = userService.findUserByEmail(email);
        return result
                .map(userEntityDTO -> ResponseEntity.ok().body(userEntityDTO))
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
     * @see UserService#findUserByFirstName(String)
     */
    @GetMapping("/user/firstname")
    public ResponseEntity<UserEntityDTO> findByFirstName(@RequestParam String firstName) {
        Optional<UserEntityDTO> result = userService.findUserByFirstName(firstName);
        return result.map(userEntityDTO -> ResponseEntity.ok().body(userEntityDTO))
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
    @GetMapping("/user")
    public ResponseEntity<UserEntityDTO> findByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<UserEntityDTO> result = userService.findByFirstNameAndLastName(firstName, lastName);
        return result.map(userEntityDTO -> ResponseEntity.ok().body(userEntityDTO))
                .orElse(ResponseEntity.notFound().build());

    }

    /**
     * Saves a new user to database .
     * <p>
     * This endpoint allows the client to create a new user by providing a valid UserDTO object in the request body.
     * The user data is validated using Bean Validation annotations.
     * {@code POST/postUser}: save new User to database
     *
     * @param userEntityDTO The UserDTO object containing the user information to be saved.
     * @return the {@link ResponseEntity} with status {@code 200(OK)} user was successfully saved in database
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#saveUser(UserEntityDTO)
     */
    @PostMapping("/user")
    public ResponseEntity<UserEntityDTO> saveUser(@Valid @RequestBody UserEntityDTO userEntityDTO) {
        UserEntityDTO result = userService.saveUser(userEntityDTO);
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
     * @param userEntityDTO The UserDTO object containing the updated user information.
     * @return the {@link ResponseEntity} with status {@code 200(OK) user was successfully updated}.
     * or with status {@code 400(Bad Request)} if the request is not valid,
     * or with status {@code 404(Not Found)} if user which should be updated is not exist in database,
     * or with status {@code 500(Internal Server Error)}when the server error was occurred
     * @see UserService#updateUser(UserEntityDTO)
     */
    @PutMapping("/user")
    public ResponseEntity<UserEntityDTO> updateUser(@Valid @RequestBody UserEntityDTO userEntityDTO) {
        UserEntityDTO userEntityDTO2 = userService.updateUser(userEntityDTO);
        return ResponseEntity.ok()
                .body(userEntityDTO2);
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
    @DeleteMapping("/user")
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


