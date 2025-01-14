package com.example.wallet_api.Repository;

import com.example.wallet_api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
