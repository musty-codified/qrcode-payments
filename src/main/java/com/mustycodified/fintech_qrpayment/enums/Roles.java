package com.mustycodified.fintech_qrpayment.enums;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

import static com.mustycodified.fintech_qrpayment.enums.UserPermissions.*;


@Getter
public enum Roles {
    USER(Sets.newHashSet(TRANSACTION_INITIATE, TRANSACTION_COMPLETE)),
    MERCHANT(Sets.newHashSet(TRANSACTION_READ));

    private final Set<UserPermissions> permissions;

    Roles(Set<UserPermissions> permissions){
        this.permissions = permissions;
    }
}
