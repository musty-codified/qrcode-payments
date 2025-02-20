package com.mustycodified.fintech_qrpayment.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessingRequest implements Serializable {
    private static final long serialVersionUID= 1L;
    private Long userId;
    private Long merchantId;
    private BigDecimal amount;
    private String currency;
}
