package com.cenyao.commerce.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.system.entity.SysConfig;
import com.cenyao.commerce.system.mapper.SysConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统配置查询骨架（敏感值后续脱敏）。
 */
@RestController
@RequestMapping("/api/v1/system/configs")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigMapper sysConfigMapper;

    @GetMapping
    public ApiResponse<List<SysConfig>> list() {
        List<SysConfig> list = sysConfigMapper.selectList(new LambdaQueryWrapper<SysConfig>()
                .orderByAsc(SysConfig::getConfigKey));
        return ApiResponse.ok(list);
    }
}
