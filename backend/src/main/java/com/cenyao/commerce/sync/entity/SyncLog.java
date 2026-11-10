package com.cenyao.commerce.sync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 同步日志（价格/库存/刊登/本地化/采集）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sync_log")
public class SyncLog extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** PRICE/STOCK/PUBLISH/LOCALIZE/COLLECT */
    private String syncType;

    private String bizId;

    private String channelCode;

    private String requestSummary;

    private String resultSummary;

    private Integer success;

    private Integer costMs;
}
