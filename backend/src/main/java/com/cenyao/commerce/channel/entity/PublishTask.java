package com.cenyao.commerce.channel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 刊登任务（刊登前须过本地化转换）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("publish_task")
public class PublishTask extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long productId;

    private Long channelAccountId;

    private String channelCode;

    private String targetLanguage;

    /** PENDING/LOCALIZING/PUBLISHING/SUCCESS/FAILED */
    private String status;

    private String externalItemId;

    private String localizedTitle;

    private String errorMessage;
}
