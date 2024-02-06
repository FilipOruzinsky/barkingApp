package com.k3project.demo.controller;

import com.k3project.demo.repository.UserRepository;
import com.k3project.demo.service.dto.UserDTO;
import com.k3project.demo.entity.User;
import com.k3project.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* ako mohol byt prvy controller ked tu uz sa vola instancia servici
co je optional
 */
@RestController
@RequestMapping(path = "api_01/users")
public class UserController {
   private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers")
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/email/{email}")
    public Optional<UserDTO> findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
    @GetMapping(value ="/firstName/{firstName}")
    public Optional<UserDTO> findByfirstName(@PathVariable String firstName){
        return userService.findUserByfirstName(firstName);
    }
    @GetMapping("/findUserByfullName/{firstName}/{lastName}")
    public Optional<UserDTO> findByfirstNameAndlastName(@PathVariable String firstName, @PathVariable String lastName) {
        System.out.println( "tusom" + " " + userRepository.findByfirstNameAndLastName(firstName, lastName));
       return userService.findByfirstNameAndlastName(firstName,lastName);

    }

    //ked som manualne zadal ID usera nefungoval post request potom mi napadlo ze UUID generuje id
    @PostMapping(value = "/postUser")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    //bez value nefungoval putrequest
    @PutMapping(value = "/putToUser")
    public User updateUser(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }
}
