package com.example.wallet_api.Repository;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Models.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveWallet() {
        // arrange
        User user = new User();
        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);
        wallet.setOwner(savedUser);

        // act
        Wallet savedWallet = walletRepository.save(wallet);

        // assert
        assertNotNull(savedWallet.getId());
        assertEquals(100.0, savedWallet.getBalance());
        assertEquals(savedUser.getId(), savedWallet.getOwner().getId());
    }

    @Test
    void testFindWalletById() {
        // arrange
        User user = new User();
        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(200.0);
        wallet.setOwner(savedUser);
        Wallet savedWallet = walletRepository.save(wallet);

        // act
        Optional<Wallet> foundWallet = walletRepository.findById(savedWallet.getId());

        // assert
        assertTrue(foundWallet.isPresent());
        assertEquals(200.0, foundWallet.get().getBalance());
        assertEquals(savedUser.getId(), foundWallet.get().getOwner().getId());
    }

    @Test
    void testFindWalletByIdNotFound() {
        // act
        Optional<Wallet> foundWallet = walletRepository.findById(999L);

        // assert
        assertFalse(foundWallet.isPresent());
    }

    @Test
    void testUpdateWalletBalance() {
        // arrange
        User user = new User();
        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(300.0);
        wallet.setOwner(savedUser);
        Wallet savedWallet = walletRepository.save(wallet);

        // act
        savedWallet.setBalance(500.0);
        Wallet updatedWallet = walletRepository.save(savedWallet);

        // assert
        assertEquals(savedWallet.getId(), updatedWallet.getId());
        assertEquals(500.0, updatedWallet.getBalance());
    }

    @Test
    void testDeleteWallet() {
        // arrange
        User user = new User();
        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setOwner(savedUser);
        Wallet savedWallet = walletRepository.save(wallet);

        // act
        walletRepository.delete(savedWallet);
        Optional<Wallet> deletedWallet = walletRepository.findById(savedWallet.getId());

        // assert
        assertFalse(deletedWallet.isPresent());
    }
}
