package com.company.bookkeeping.auth;

import com.company.bookkeeping.user.Permission;
import com.company.bookkeeping.user.Role;
import com.company.bookkeeping.user.UserAccount;

import java.util.Set;

public record SessionInfo(String token, UserAccount user) {
    public boolean hasPermission(Permission permission) {
        return user.getRoles().stream().map(Role::permissions).anyMatch(perms -> perms.contains(permission));
    }

    public String username() {
        return user.getUsername();
    }

    public Set<Role> roles() {
        return user.getRoles();
    }
}
