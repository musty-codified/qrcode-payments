package com.mustycodified.fintech_qrpayment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Long userId;
    private String name;
    private String accountNumber;
    private String phoneNumber;
    private String emailAddress;
    private BigDecimal availableBalance;
    private String walletStatus;
    private BigDecimal amount;



}
