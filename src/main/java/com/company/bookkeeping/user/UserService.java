package com.company.bookkeeping.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<String, UserAccount> usersByUsername = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserService() {
        UserAccount admin = new UserAccount(idGenerator.getAndIncrement(), "admin", "admin123", Set.of(Role.ADMIN), 3);
        usersByUsername.put(admin.getUsername(), admin);
    }

    public UserAccount create(CreateUserRequest request) {
        if (usersByUsername.containsKey(request.username())) {
            throw new IllegalArgumentException("用户名已存在");
        }
        UserAccount account = new UserAccount(
                idGenerator.getAndIncrement(),
                request.username(),
                request.password(),
                request.roles(),
                request.maxConcurrentSessions()
        );
        usersByUsername.put(account.getUsername(), account);
        return account;
    }

    public Optional<UserAccount> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    public List<UserResponse> list() {
        return new ArrayList<>(usersByUsername.values()).stream().map(UserResponse::from).toList();
    }
}
