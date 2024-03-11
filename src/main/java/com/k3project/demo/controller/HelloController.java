package com.k3project.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v2")

public class HelloController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello wordl!";
    }
}
