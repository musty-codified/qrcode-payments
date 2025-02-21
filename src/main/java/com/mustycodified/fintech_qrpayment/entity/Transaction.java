package com.mustycodified.fintech_qrpayment.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private Long transactionId;
    private Long userId;
    private Long merchantId;
    private BigDecimal amount;
    private String currency;
    private String status;

    public Transaction(Long userId, Long merchantId, BigDecimal amount, String currency, String status) {
        this.userId = userId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }
}
