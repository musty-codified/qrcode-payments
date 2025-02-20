package com.mustycodified.fintech_qrpayment.repository;

import com.mustycodified.fintech_qrpayment.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(@NotBlank(message = "Email is required") String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

}
