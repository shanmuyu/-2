package com.company.bookkeeping.user;

import java.util.Set;

public record UserResponse(Long id, String username, Set<Role> roles, int maxConcurrentSessions) {
    public static UserResponse from(UserAccount user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRoles(), user.getMaxConcurrentSessions());
    }
}
