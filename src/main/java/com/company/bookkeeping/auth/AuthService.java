package com.company.bookkeeping.auth;

import com.company.bookkeeping.user.UserAccount;
import com.company.bookkeeping.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final UserService userService;
    private final Map<String, SessionInfo> sessionByToken = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> tokensByUsername = new ConcurrentHashMap<>();

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public LoginResponse login(LoginRequest request) {
        UserAccount user = userService.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("账号或密码错误"));
        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        Set<String> activeTokens = tokensByUsername.computeIfAbsent(user.getUsername(), key -> ConcurrentHashMap.newKeySet());
        if (activeTokens.size() >= user.getMaxConcurrentSessions()) {
            throw new IllegalStateException("已达到账号最大同时在线数");
        }

        String token = UUID.randomUUID().toString();
        activeTokens.add(token);
        sessionByToken.put(token, new SessionInfo(token, user));
        return new LoginResponse(token, user.getUsername(), user.getRoles());
    }

    public void logout(String token) {
        SessionInfo info = sessionByToken.remove(token);
        if (info != null) {
            Set<String> tokens = tokensByUsername.get(info.username());
            if (tokens != null) {
                tokens.remove(token);
            }
        }
    }

    public SessionInfo requireSession(String token) {
        SessionInfo info = sessionByToken.get(token);
        if (info == null) {
            throw new IllegalArgumentException("无效登录态，请重新登录");
        }
        return info;
    }
}
