package com.company.bookkeeping.common;

import com.company.bookkeeping.auth.AuthService;
import com.company.bookkeeping.auth.SessionInfo;
import com.company.bookkeeping.user.Permission;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final AuthService authService;

    public AuthContext(AuthService authService) {
        this.authService = authService;
    }

    public SessionInfo requirePermission(String authorizationHeader, Permission permission) {
        String token = extractToken(authorizationHeader);
        SessionInfo info = authService.requireSession(token);
        if (!info.hasPermission(permission)) {
            throw new IllegalStateException("没有权限执行此操作");
        }
        return info;
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            throw new IllegalArgumentException("请提供 Bearer Token");
        }
        return authorizationHeader.substring(TOKEN_PREFIX.length());
    }
}
