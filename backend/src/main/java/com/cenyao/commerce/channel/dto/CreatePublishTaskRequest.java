package com.cenyao.commerce.channel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建刊登任务。
 */
@Data
public class CreatePublishTaskRequest {

    @NotNull(message = "商品 ID 不能为空")
    private Long productId;

    @NotNull(message = "渠道账号 ID 不能为空")
    private Long channelAccountId;
}
