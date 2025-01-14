package com.example.wallet_api.Controllers;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Services.UserService;
import com.example.wallet_api.DTOs.DTO_CreateUser;
import com.example.wallet_api.Interfaces.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody DTO_CreateUser user_dto) {
        try {
            long userId = userService.createUser(user_dto);
            if (userId == -1) {
                return ResponseEntity.badRequest().body("User creation failed: User already exists or an error occurred");
            }
            return ResponseEntity.ok("User created successfully with ID: " + userId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("User creation failed: " + e.getMessage());
        }
    }
}
