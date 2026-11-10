package com.cenyao.commerce.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.task.dto.CreateCollectTaskRequest;
import com.cenyao.commerce.task.entity.CollectTask;
import com.cenyao.commerce.task.mapper.CollectTaskMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 采集任务：创建 / 执行 / 查询。
 */
@RestController
@RequestMapping("/api/v1/collect-tasks")
@RequiredArgsConstructor
public class CollectTaskController {

    private final CollectTaskMapper collectTaskMapper;
    private final CollectTaskService collectTaskService;

    @GetMapping
    public ApiResponse<Page<CollectTask>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size) {
        Page<CollectTask> result = collectTaskMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<CollectTask>().orderByDesc(CollectTask::getId));
        return ApiResponse.ok(result);
    }

    @PostMapping
    public ApiResponse<CollectTask> create(@Valid @RequestBody CreateCollectTaskRequest request) {
        return ApiResponse.ok(collectTaskService.create(request));
    }

    @PostMapping("/{id}/run")
    public ApiResponse<Map<String, Object>> run(@PathVariable Long id) {
        return ApiResponse.ok(collectTaskService.run(id));
    }
}
