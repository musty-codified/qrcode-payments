package com.mustycodified.fintech_qrpayment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private BigDecimal userBalance;
    private BigDecimal merchantBalance;

}
