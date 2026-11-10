package com.cenyao.commerce.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品图片。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_image")
public class ProductImage extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long productId;

    private String originUrl;

    private String objectKey;

    private Integer sortOrder;

    /** MAIN / DETAIL */
    private String usageType;
}
