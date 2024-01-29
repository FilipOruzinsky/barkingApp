package com.k3project.demo.controller;

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
@RequestMapping(path ="api_01/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value="/allUsers")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{userId}")
    public Optional<User>findUserById(@PathVariable("userId")UUID userId){
        return userService.findById(userId);
    }

    //ked som manualne zadal ID usera nefungoval post request potom mi napadlo ze UUID generuje id
    @PostMapping(value = "/postUser")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    //bez value nefungoval putrequest
    @PutMapping(value = "/putToUser")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable ("userId") UUID userId){
        userService.deleteUser(userId);
    }
}
