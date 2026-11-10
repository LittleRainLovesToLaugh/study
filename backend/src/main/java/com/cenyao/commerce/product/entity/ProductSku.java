package com.cenyao.commerce.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品规格。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_sku")
public class ProductSku extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long productId;

    private String skuCode;

    private String barcode;

    private String specJson;

    private BigDecimal price;

    private Integer stock;

    private Integer weightGram;

    private Integer lengthMm;

    private Integer widthMm;

    private Integer heightMm;
}
