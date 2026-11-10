package com.cenyao.commerce.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.task.entity.CollectTask;
import com.cenyao.commerce.task.mapper.CollectTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采集任务查询骨架。
 */
@RestController
@RequestMapping("/api/v1/collect-tasks")
@RequiredArgsConstructor
public class CollectTaskController {

    private final CollectTaskMapper collectTaskMapper;

    @GetMapping
    public ApiResponse<Page<CollectTask>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size) {
        Page<CollectTask> result = collectTaskMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<CollectTask>().orderByDesc(CollectTask::getId));
        return ApiResponse.ok(result);
    }
}
