package com.example.wallet_api.Services;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Models.Wallet;
import com.example.wallet_api.Repository.UserRepository;
import com.example.wallet_api.Repository.WalletRepository;
import com.example.wallet_api.DTOs.DTO_CreateWallet;
import com.example.wallet_api.DTOs.DTO_ExistingWallet;
import com.example.wallet_api.DTOs.DTO_RequestWallet;
import com.example.wallet_api.DTOs.DTO_UpdateWallet;
import com.example.wallet_api.Interfaces.IWalletService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService implements IWalletService{

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long createWallet(DTO_CreateWallet wallet_dto) {
        try {
            User user = userRepository.findById(wallet_dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Wallet wallet = new Wallet();
            wallet.setBalance(wallet_dto.getBalance());
            wallet.setOwner(user);

            walletRepository.save(wallet);
            return wallet.getId();
        } catch (Exception e) {
            System.err.println("Error during wallet creation: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public Optional<DTO_ExistingWallet> getExistingWallet(DTO_RequestWallet dto_requestWallet) {
        try {
            Wallet wallet = walletRepository.findById(dto_requestWallet.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            if (!wallet.getOwner().getId().equals(dto_requestWallet.getUserId())) {
                throw new RuntimeException("Unauthorized access: Wallet does not belong to the user");
            }

            DTO_ExistingWallet dto_existingWallet = new DTO_ExistingWallet();
            dto_existingWallet.setWalletId(wallet.getId());
            dto_existingWallet.setBalance(wallet.getBalance());
            dto_existingWallet.setUserId(wallet.getOwner().getId());

            return Optional.of(dto_existingWallet);
        } catch (Exception e) {
            System.err.println("Error in getExistingWallet: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deposit(DTO_UpdateWallet wallet_dto) {
        try {
            Wallet wallet = walletRepository.findById(wallet_dto.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            if (!wallet.getOwner().getId().equals(wallet_dto.getUserId())) {
                throw new RuntimeException("Unauthorized access: Wallet does not belong to the user");
            }

            wallet.setBalance(wallet.getBalance() + wallet_dto.getAmount());

            walletRepository.save(wallet);
            return true;
        } catch (Exception e) {
            System.err.println("Error during deposit: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean withdraw(DTO_UpdateWallet wallet_dto) {
        try {
            Wallet wallet = walletRepository.findById(wallet_dto.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            if (!wallet.getOwner().getId().equals(wallet_dto.getUserId())) {
                throw new RuntimeException("Unauthorized access: Wallet does not belong to the user");
            }
            if (wallet.getBalance() < wallet_dto.getAmount()) {
                throw new RuntimeException("Insufficient balance");
            }

            wallet.setBalance(wallet.getBalance() - wallet_dto.getAmount());

            walletRepository.save(wallet);
            return true;
        } catch (Exception e) {
            System.err.println("Error during withdrawal: " + e.getMessage());
            return false;
        }
    }
}
