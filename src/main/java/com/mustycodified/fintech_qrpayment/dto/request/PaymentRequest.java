package com.mustycodified.fintech_qrpayment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest implements Serializable {
    private static final long serialVersionUID= 1L;

    private BigDecimal amount;
    private String currency;
    private Long merchantId;
    private String description;

}
