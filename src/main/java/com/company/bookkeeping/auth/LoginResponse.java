package com.company.bookkeeping.auth;

import com.company.bookkeeping.user.Role;

import java.util.Set;

public record LoginResponse(String token, String username, Set<Role> roles) {
}
