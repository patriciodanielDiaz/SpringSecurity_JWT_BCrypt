package com.demoSecurity.demo.controller;

import com.demoSecurity.demo.model.User;
import com.demoSecurity.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){this.userService=userService; this.bCryptPasswordEncoder=bCryptPasswordEncoder;}

    @GetMapping("/")
    public String WelcomePagePublic() {
        return "[ZONA PUBLICA] Bienvenido, estás en la parte pública de la web!";
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(Authentication authentication){
        return userService.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user){

        String passEncoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passEncoded);
        return userService.addUser(user);
    }

}
