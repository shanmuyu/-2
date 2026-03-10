package com.company.bookkeeping.user;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    ADMIN(EnumSet.allOf(Permission.class)),
    ACCOUNTANT(EnumSet.of(Permission.MANAGE_ITEMS, Permission.MANAGE_BILLS, Permission.VIEW_DASHBOARD)),
    VIEWER(EnumSet.of(Permission.VIEW_DASHBOARD));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> permissions() {
        return permissions;
    }
}
