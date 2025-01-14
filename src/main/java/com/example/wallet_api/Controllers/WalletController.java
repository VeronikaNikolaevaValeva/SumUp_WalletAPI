package com.example.wallet_api.controller;

import com.example.wallet_api.Models.User;
import com.example.wallet_api.Models.Wallet;
import com.example.wallet_api.Services.UserService;
import com.example.wallet_api.Services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }


}
