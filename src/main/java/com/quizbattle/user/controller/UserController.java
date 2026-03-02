package com.quizbattle.user.controller;

import com.quizbattle.security.FirebaseUser;
import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        FirebaseUser firebaseUser =
                (FirebaseUser) authentication.getPrincipal();

        return userService.getOrCreateUser(firebaseUser);

    }

}