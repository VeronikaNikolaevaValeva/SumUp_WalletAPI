package com.example.wallet_api.Interfaces;

import com.example.wallet_api.DTOs.DTO_CreateWallet;
import com.example.wallet_api.DTOs.DTO_ExistingWallet;
import com.example.wallet_api.DTOs.DTO_RequestWallet;
import com.example.wallet_api.DTOs.DTO_UpdateWallet;

import java.util.Optional;

public interface IWalletService {
    long createWallet(DTO_CreateWallet wallet_dto);
    Optional<DTO_ExistingWallet> getExistingWallet(DTO_RequestWallet dto_requestWallet);
    boolean deposit(DTO_UpdateWallet wallet_dto);
    boolean withdraw(DTO_UpdateWallet wallet_dto);
}