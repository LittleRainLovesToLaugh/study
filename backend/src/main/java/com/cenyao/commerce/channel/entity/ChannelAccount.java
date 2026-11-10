package com.cenyao.commerce.channel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道账号（一期仅虾皮店铺；凭证字段存密文）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("channel_account")
public class ChannelAccount extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 一期仅 SHOPEE */
    private String channelCode;

    private String shopName;

    private String shopId;

    private String partnerIdCipher;

    private String partnerKeyCipher;

    private String accessTokenCipher;

    private String refreshTokenCipher;

    /** 虾皮站点/地区，如 TW/MY/SG */
    private String siteRegion;

    /** 目标语言，如 zh-TW/en */
    private String targetLanguage;

    private Integer status;
}
