package com.sucu.SpringBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping(path = "/")
    public String getWelcome(){
        return "Welcome to Everyone";
    }

    @GetMapping(path = "/hello")
    public String getHello(){
        return "Hello, World";
    }

}
