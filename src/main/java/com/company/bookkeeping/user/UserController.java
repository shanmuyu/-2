package com.company.bookkeeping.user;

import com.company.bookkeeping.common.AuthContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthContext authContext;

    public UserController(UserService userService, AuthContext authContext) {
        this.userService = userService;
        this.authContext = authContext;
    }

    @PostMapping
    public UserResponse create(@RequestHeader("Authorization") String authorizationHeader,
                               @Valid @RequestBody CreateUserRequest request) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_USERS);
        return UserResponse.from(userService.create(request));
    }

    @GetMapping
    public List<UserResponse> list(@RequestHeader("Authorization") String authorizationHeader) {
        authContext.requirePermission(authorizationHeader, Permission.MANAGE_USERS);
        return userService.list();
    }
}
