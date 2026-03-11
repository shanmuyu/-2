package com.company.bookkeeping.user;

import java.util.Set;

public class UserAccount {
    private final Long id;
    private final String username;
    private String password;
    private Set<Role> roles;
    private int maxConcurrentSessions;

    public UserAccount(Long id, String username, String password, Set<Role> roles, int maxConcurrentSessions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.maxConcurrentSessions = maxConcurrentSessions;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public int getMaxConcurrentSessions() {
        return maxConcurrentSessions;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setMaxConcurrentSessions(int maxConcurrentSessions) {
        this.maxConcurrentSessions = maxConcurrentSessions;
    }
}
