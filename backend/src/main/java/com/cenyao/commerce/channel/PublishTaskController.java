package com.cenyao.commerce.channel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.channel.dto.CreatePublishTaskRequest;
import com.cenyao.commerce.channel.entity.PublishTask;
import com.cenyao.commerce.channel.mapper.PublishTaskMapper;
import com.cenyao.commerce.common.ApiResponse;
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
 * 刊登任务：创建 / 执行（本地化+虾皮 Stub）/ 查询。
 */
@RestController
@RequestMapping("/api/v1/publish-tasks")
@RequiredArgsConstructor
public class PublishTaskController {

    private final PublishTaskMapper publishTaskMapper;
    private final PublishTaskService publishTaskService;

    @GetMapping
    public ApiResponse<Page<PublishTask>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size) {
        Page<PublishTask> result = publishTaskMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<PublishTask>().orderByDesc(PublishTask::getId));
        return ApiResponse.ok(result);
    }

    @PostMapping
    public ApiResponse<PublishTask> create(@Valid @RequestBody CreatePublishTaskRequest request) {
        return ApiResponse.ok(publishTaskService.create(request));
    }

    @PostMapping("/{id}/run")
    public ApiResponse<Map<String, Object>> run(@PathVariable Long id) {
        return ApiResponse.ok(publishTaskService.run(id));
    }
}
