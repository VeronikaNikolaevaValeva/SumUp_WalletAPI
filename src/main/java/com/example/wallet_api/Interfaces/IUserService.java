package com.example.wallet_api.Interfaces;

import com.example.wallet_api.DTOs.DTO_CreateUser;

public interface IUserService {
    long createUser(DTO_CreateUser user_dto);
}