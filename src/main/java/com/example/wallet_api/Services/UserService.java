package com.example.wallet_api.Services;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Repository.UserRepository;
import com.example.wallet_api.DTOs.DTO_CreateUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createUser(DTO_CreateUser user_dto) {
        try {
            User user = new User();
            user.setName(user_dto.getName());

            userRepository.save(user);
            return user.getId();
        } catch (Exception e) {
            System.err.println("Error during user creation: " + e.getMessage());
            return -1;
        }
    }
}
