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
public class Merchant {
    private Long merchantId;
    private String businessName;
    private String accountNumber;
    private String phoneNumber;
    private String emailAddress;
    private BigDecimal availableBalance;
    private String walletStatus;


}
