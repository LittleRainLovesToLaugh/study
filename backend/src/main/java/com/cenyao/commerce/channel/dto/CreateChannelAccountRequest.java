package com.cenyao.commerce.channel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建虾皮渠道账号（凭证明文仅在服务端加密入库，接口不回传）。
 */
@Data
public class CreateChannelAccountRequest {

    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

    private String shopId;

    /** 虾皮站点/地区，如 TW/MY/SG */
    @NotBlank(message = "站点/地区不能为空")
    private String siteRegion;

    /** 目标语言，如 zh-TW/en */
    @NotBlank(message = "目标语言不能为空")
    private String targetLanguage;

    /** 合作方编号明文（可选，占位联调） */
    private String partnerId;

    /** 合作方密钥明文（可选） */
    private String partnerKey;

    /** 店铺授权令牌明文（可选） */
    private String accessToken;
}
