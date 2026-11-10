package com.cenyao.commerce.auth;

import com.cenyao.commerce.auth.dto.LoginRequest;
import com.cenyao.commerce.auth.dto.LoginResponse;
import com.cenyao.commerce.auth.service.AuthService;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.security.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证接口：登录与当前用户。
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(Authentication authentication) {
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return ApiResponse.ok(Map.of(
                "userId", user.userId(),
                "username", user.username(),
                "roleCode", user.roleCode()
        ));
    }
}
