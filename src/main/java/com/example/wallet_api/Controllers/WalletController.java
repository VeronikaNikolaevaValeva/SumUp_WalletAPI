package com.example.wallet_api.Controllers;

import com.example.wallet_api.Models.Wallet;
import com.example.wallet_api.Services.WalletService;
import com.example.wallet_api.DTOs.DTO_CreateWallet;
import com.example.wallet_api.DTOs.DTO_ExistingWallet;
import com.example.wallet_api.DTOs.DTO_RequestWallet;
import com.example.wallet_api.DTOs.DTO_UpdateWallet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/createWallet")
    public ResponseEntity<String> createWallet(@RequestBody DTO_CreateWallet wallet_dto) {
        try {
            long walletId = walletService.createWallet(wallet_dto);
            if (walletId == -1) {
                return ResponseEntity.badRequest().body("Wallet creation failed: Unable to create wallet");
            }
            return ResponseEntity.ok("Wallet created successfully with ID: " + walletId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Wallet creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/getWallet")
    public ResponseEntity<?> getWallet(@RequestBody DTO_RequestWallet dto_requestWallet) {
        Optional<DTO_ExistingWallet> existingWallet = walletService.getExistingWallet(dto_requestWallet);

        if (existingWallet.isPresent()) {
            return ResponseEntity.ok(existingWallet.get());
        } else {
            return ResponseEntity.badRequest().body("Wallet not found or unauthorized access");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DTO_UpdateWallet wallet_dto) {
        boolean success = walletService.deposit(wallet_dto);
        if (success) {
            return ResponseEntity.ok("Deposit successful");
        } else {
            return ResponseEntity.badRequest().body("Deposit failed");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DTO_UpdateWallet wallet_dto) {
        boolean success = walletService.withdraw(wallet_dto);
        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.badRequest().body("Withdrawal failed");
        }
    }


}
