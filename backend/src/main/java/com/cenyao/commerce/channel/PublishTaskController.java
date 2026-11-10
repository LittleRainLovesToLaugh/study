package com.cenyao.commerce.channel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.channel.entity.PublishTask;
import com.cenyao.commerce.channel.mapper.PublishTaskMapper;
import com.cenyao.commerce.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 刊登任务查询骨架。
 */
@RestController
@RequestMapping("/api/v1/publish-tasks")
@RequiredArgsConstructor
public class PublishTaskController {

    private final PublishTaskMapper publishTaskMapper;

    @GetMapping
    public ApiResponse<Page<PublishTask>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size) {
        Page<PublishTask> result = publishTaskMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<PublishTask>().orderByDesc(PublishTask::getId));
        return ApiResponse.ok(result);
    }
}
