package com.cenyao.commerce.security;

/**
 * 当前登录用户（安全上下文主体）。
 */
public record LoginUser(Long userId, String username, String roleCode) {
}
