package com.cenyao.commerce.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建采集任务。
 */
@Data
public class CreateCollectTaskRequest {

    /** 1688 货源编号（官方接口主键） */
    @NotBlank(message = "货源编号不能为空")
    private String sourceOfferId;

    /** 货源链接（可选，仅存档） */
    private String sourceUrl;

    private Integer priority = 0;
}
