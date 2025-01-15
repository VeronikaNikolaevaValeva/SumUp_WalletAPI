package com.example.wallet_api.Repository;

import com.example.wallet_api.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        // arrange
        User user = new User();
        user.setName("Veronika");

        // act
        User savedUser = userRepository.save(user);

        // assert
        assertNotNull(savedUser.getId());
        assertEquals("Veronika", savedUser.getName());
    }

    @Test
    void testFindUserById() {
        // arrange
        User user = new User();
        user.setName("Veronika");
        User savedUser = userRepository.save(user);

        // act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // assert
        assertTrue(foundUser.isPresent());
        assertEquals("Veronika", foundUser.get().getName());
    }

    @Test
    void testFindUserByIdNotFound() {
        // act
        Optional<User> foundUser = userRepository.findById(1L);

        // assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testUpdateUser() {
        // arrange
        User user = new User();
        user.setName("Veronika");
        User savedUser = userRepository.save(user);

        // act
        savedUser.setName("Valeva");
        User updatedUser = userRepository.save(savedUser);

        // assert
        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals("Valeva", updatedUser.getName());
    }

    @Test
    void testDeleteUser() {
        // arrange
        User user = new User();
        user.setName("Veronika");
        User savedUser = userRepository.save(user);

        // act
        userRepository.delete(savedUser);
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        // assert
        assertFalse(deletedUser.isPresent());
    }
}
