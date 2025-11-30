package com.example.backend.dto.response;

import java.time.LocalDateTime;

public class AuthResponse {
    private String token;
    private String userId;
    private String fullName;
    private String role;
    private String email;
    private LocalDateTime expiresAt;

    public AuthResponse() {}

    public AuthResponse(String token, String userId, String fullName, String role,
                        String email, LocalDateTime expiresAt) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
        this.email = email;
        this.expiresAt = expiresAt;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}

