package com.company.bookkeeping;

import com.company.bookkeeping.auth.AuthService;
import com.company.bookkeeping.auth.LoginRequest;
import com.company.bookkeeping.user.CreateUserRequest;
import com.company.bookkeeping.user.Role;
import com.company.bookkeeping.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Test
    void shouldRejectLoginWhenExceedMaxConcurrentSessions() {
        userService.create(new CreateUserRequest("u1", "p1", Set.of(Role.VIEWER), 1));

        authService.login(new LoginRequest("u1", "p1"));

        assertThrows(IllegalStateException.class, () -> authService.login(new LoginRequest("u1", "p1")));
    }
}
