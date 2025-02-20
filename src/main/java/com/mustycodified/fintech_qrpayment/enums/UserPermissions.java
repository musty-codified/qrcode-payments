package com.mustycodified.fintech_qrpayment.enums;

import lombok.Getter;

@Getter
public enum UserPermissions {
    TRANSACTION_INITIATE("transaction.initiate"),
    TRANSACTION_COMPLETE("transaction.complete"),
    TRANSACTION_READ("transaction.read");
    private final String permission;
    UserPermissions(String permission){
        this.permission=permission;
    }

}
