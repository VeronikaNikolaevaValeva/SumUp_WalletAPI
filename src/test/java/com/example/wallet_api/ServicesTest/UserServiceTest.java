package com.example.wallet_api.Services;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Repository.UserRepository;
import com.example.wallet_api.Interfaces.IUserService;
import com.example.wallet_api.DTOs.DTO_CreateUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUserSuccess() {
        // Arrange
        DTO_CreateUser userDto = new DTO_CreateUser();
        userDto.setName("Veronika");

        //User user = new User();
        //user.setName("Veronika");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Set the ID explicitly
            return user;
        });

        // Act
        long userId = userService.createUser(userDto);

        // Assert
        assertEquals(1L, userId);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
