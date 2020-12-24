package com.spring.files.controller;

import com.spring.files.entity.User;
import com.spring.files.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(User user, HttpSession session) {
        System.out.println("进入login");
        User userDB = userService.login(user);
        System.out.println("获取对象"+userDB);
        if (userDB != null) {
            session.setAttribute("user",userDB);
            System.out.println("准备跳转");
            return "redirect:/file/findAll";
        } else {
            return "redirect:/login";
            //由controller跳转道login，直接重定向到html会导致解析不了thymeleaf语法
        }
    }
}
