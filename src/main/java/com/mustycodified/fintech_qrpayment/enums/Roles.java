package com.mustycodified.fintech_qrpayment.enums;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

import static com.mustycodified.fintech_qrpayment.enums.UserPermissions.*;


@Getter
public enum Roles {
    ROLE_USER(Sets.newHashSet(USER_EDIT, USER_READ)),
    ROLE_ADMIN(Sets.newHashSet(USER_DELETE, USER_READ, USER_EDIT));
    private final Set<UserPermissions> permissions;

    Roles(Set<UserPermissions> permissions){
        this.permissions = permissions;
    }
}
