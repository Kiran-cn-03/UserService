package org.example.userservice.controllers;

import org.example.userservice.dtos.LoginRequestDTO;
import org.example.userservice.dtos.SignUpRequestDTO;
import org.example.userservice.dtos.UserResponseDTO;
import org.example.userservice.models.User;
import org.example.userservice.models.Token;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        User user = userService.signUp(signUpRequestDTO.getEmail(),signUpRequestDTO.getUsername()
                ,signUpRequestDTO.getPassword());
        return UserResponseDTO.fromUser(user);
    }
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Token token = userService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
        return token;
    }

    @GetMapping("/validate/{token}")
    public UserResponseDTO validateToken(@PathVariable String token){
        User user = userService.validateToken(token);
        return UserResponseDTO.fromUser(user);
    }
}
