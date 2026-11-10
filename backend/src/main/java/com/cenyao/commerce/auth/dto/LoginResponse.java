package com.cenyao.commerce.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录响应。
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private String username;
    private String displayName;
    private String roleCode;
}
