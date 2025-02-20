package com.mustycodified.fintech_qrpayment.enums;

import lombok.Getter;

@Getter
public enum UserPermissions {
    USER_READ("user.read"),
    USER_EDIT("user.edit"),
    USER_DELETE("user.delete");

    private final String permission;
    UserPermissions(String permission){
        this.permission=permission;
    }

}
