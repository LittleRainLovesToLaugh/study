package com.cenyao.commerce.sync;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.sync.entity.SyncLog;
import com.cenyao.commerce.sync.mapper.SyncLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同步日志查询骨架。
 */
@RestController
@RequestMapping("/api/v1/sync-logs")
@RequiredArgsConstructor
public class SyncLogController {

    private final SyncLogMapper syncLogMapper;

    @GetMapping
    public ApiResponse<Page<SyncLog>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size,
            @RequestParam(required = false) String syncType) {
        LambdaQueryWrapper<SyncLog> wrapper = new LambdaQueryWrapper<SyncLog>()
                .eq(syncType != null && !syncType.isBlank(), SyncLog::getSyncType, syncType)
                .orderByDesc(SyncLog::getId);
        return ApiResponse.ok(syncLogMapper.selectPage(new Page<>(page, size), wrapper));
    }
}
