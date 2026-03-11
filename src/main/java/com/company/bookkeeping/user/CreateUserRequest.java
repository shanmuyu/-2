package com.company.bookkeeping.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotEmpty Set<Role> roles,
        @Min(1) int maxConcurrentSessions
) {
}
