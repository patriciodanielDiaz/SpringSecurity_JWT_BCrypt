package com.demoSecurity.demo.controller;

import com.demoSecurity.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService){this.userService=userService;}

    @GetMapping("/")
    public String welcomePage(Authentication authentication) {
        return "Bienvenido, tienes permisos de "+authentication.getName() ;}

}
