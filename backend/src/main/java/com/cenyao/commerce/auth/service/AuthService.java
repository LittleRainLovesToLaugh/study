package com.cenyao.commerce.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.auth.dto.LoginRequest;
import com.cenyao.commerce.auth.dto.LoginResponse;
import com.cenyao.commerce.auth.entity.SysUser;
import com.cenyao.commerce.auth.mapper.SysUserMapper;
import com.cenyao.commerce.common.BizException;
import com.cenyao.commerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 登录鉴权服务。
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, request.getUsername()));
        if (user == null || user.getStatus() == null || user.getStatus() != 1) {
            throw BizException.unauthorized("用户名或密码错误");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw BizException.unauthorized("用户名或密码错误");
        }
        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRoleCode());
        return new LoginResponse(token, user.getId(), user.getUsername(), user.getDisplayName(), user.getRoleCode());
    }
}
