package com.cenyao.commerce.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采集任务。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("collect_task")
public class CollectTask extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String sourceUrl;

    private String sourceOfferId;

    private Integer priority;

    /** PENDING/RUNNING/SUCCESS/FAILED/CANCELLED */
    private String status;

    private Long operatorId;

    private String errorMessage;

    private Integer retryCount;
}
