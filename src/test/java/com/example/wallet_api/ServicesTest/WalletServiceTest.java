package com.example.wallet_api.Services;

import com.example.wallet_api.DTOs.DTO_CreateWallet;
import com.example.wallet_api.DTOs.DTO_ExistingWallet;
import com.example.wallet_api.DTOs.DTO_RequestWallet;
import com.example.wallet_api.DTOs.DTO_UpdateWallet;
import com.example.wallet_api.Models.User;
import com.example.wallet_api.Models.Wallet;
import com.example.wallet_api.Repository.UserRepository;
import com.example.wallet_api.Repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    private User mockUser;
    private Wallet mockWallet;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        mockWallet = new Wallet();
        mockWallet.setId(1L);
        mockWallet.setBalance(100.0);
        mockWallet.setOwner(mockUser);
    }

    @Test
    void testCreateWalletSuccess() {
        // arrange
        DTO_CreateWallet walletDto = new DTO_CreateWallet();
        walletDto.setUserId(1L);
        walletDto.setBalance(100.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> {
            Wallet wallet = invocation.getArgument(0);
            wallet.setId(1L);
            return wallet;
        });

        // act
        long walletId = walletService.createWallet(walletDto);

        // assert
        assertEquals(1L, walletId);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testCreateWalletUserNotFound() {
        // arrange
        DTO_CreateWallet walletDto = new DTO_CreateWallet();
        walletDto.setUserId(2L);
        walletDto.setBalance(100.0);

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        // act
        long walletId = walletService.createWallet(walletDto);

        // assert
        assertEquals(-1L, walletId);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void testGetExistingWalletSuccess() {
        // arrange
        DTO_RequestWallet requestWallet = new DTO_RequestWallet();
        requestWallet.setWalletId(1L);
        requestWallet.setUserId(1L);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));

        // act
        Optional<DTO_ExistingWallet> result = walletService.getExistingWallet(requestWallet);

        // assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getWalletId());
        assertEquals(100.0, result.get().getBalance());
    }

    @Test
    void testGetExistingWalletUnauthorized() {
        // arrange
        DTO_RequestWallet requestWallet = new DTO_RequestWallet();
        requestWallet.setWalletId(1L);
        requestWallet.setUserId(2L);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));

        // act
        Optional<DTO_ExistingWallet> result = walletService.getExistingWallet(requestWallet);

        // assert
        assertFalse(result.isPresent());
    }

    @Test
    void testDepositSuccess() {
        // arrange
        DTO_UpdateWallet updateWallet = new DTO_UpdateWallet();
        updateWallet.setWalletId(1L);
        updateWallet.setUserId(1L);
        updateWallet.setAmount(50.0);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(mockWallet);

        // act
        boolean result = walletService.deposit(updateWallet);

        // assert
        assertTrue(result);
        assertEquals(150.0, mockWallet.getBalance());
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testDepositUnauthorized() {
        // arrange
        DTO_UpdateWallet updateWallet = new DTO_UpdateWallet();
        updateWallet.setWalletId(1L);
        updateWallet.setUserId(2L);
        updateWallet.setAmount(50.0);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));

        // act
        boolean result = walletService.deposit(updateWallet);

        // assert
        assertFalse(result);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void testWithdrawSuccess() {
        // arrange
        DTO_UpdateWallet updateWallet = new DTO_UpdateWallet();
        updateWallet.setWalletId(1L);
        updateWallet.setUserId(1L);
        updateWallet.setAmount(50.0);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(mockWallet);

        // act
        boolean result = walletService.withdraw(updateWallet);

        // assert
        assertTrue(result);
        assertEquals(50.0, mockWallet.getBalance());
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testWithdrawInsufficientBalance() {
        // arrange
        DTO_UpdateWallet updateWallet = new DTO_UpdateWallet();
        updateWallet.setWalletId(1L);
        updateWallet.setUserId(1L);
        updateWallet.setAmount(150.0);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));

        // act
        boolean result = walletService.withdraw(updateWallet);

        // assert
        assertFalse(result);
        assertEquals(100.0, mockWallet.getBalance());
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void testWithdrawUnauthorized() {
        // arrange
        DTO_UpdateWallet updateWallet = new DTO_UpdateWallet();
        updateWallet.setWalletId(1L);
        updateWallet.setUserId(2L);
        updateWallet.setAmount(50.0);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(mockWallet));

        // act
        boolean result = walletService.withdraw(updateWallet);

        // assert
        assertFalse(result);
        verify(walletRepository, never()).save(any(Wallet.class));
    }
}
