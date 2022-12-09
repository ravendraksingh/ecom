package com.rks.userservice.controllers;

import com.rks.userservice.dto.response.UserResponse;
import com.rks.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
