package com.example.backend.controller;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService ;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
// Add this part (withCredentials) such that angular accept the session cookie
//    login(email: string, password: string, role: string) {
//        return this.http.post(
//                "http://localhost:8080/auth/login",
//                { email, password, role },
//                { withCredentials: true }    // IMPORTANT
//  );
//    }


    @PostMapping("/login")
    ApiResponse<?> login(@RequestBody LoginRequest loginRequest , HttpServletRequest request, HttpServletResponse response) {
        return authService.login(loginRequest , request, response);
    }
}
