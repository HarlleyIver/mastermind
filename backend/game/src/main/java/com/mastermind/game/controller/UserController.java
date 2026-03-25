package com.mastermind.game.controller;

import com.mastermind.game.dto.*;
import com.mastermind.game.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequest request) {
        return userService.login(request.getLogin(), request.getPassword());
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return userService.register(request);
    }
}