package com.spring.files.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("login")
    public String toLogin(){
        System.out.println("进入IndexController");
        return "login";
    }
}
