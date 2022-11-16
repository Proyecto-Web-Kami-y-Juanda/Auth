package co.edu.javeriana.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.auth.dto.User;
import co.edu.javeriana.auth.service.AuthService;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    @CrossOrigin
    public User login(@RequestParam String user, @RequestParam String password) {
        return authService.login(user, password);
    }

}

