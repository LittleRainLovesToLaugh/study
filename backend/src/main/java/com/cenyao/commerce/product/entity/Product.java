package com.cenyao.commerce.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品（标准化入库，标题多为简体中文）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product")
public class Product extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String sourceOfferId;

    private String title;

    private String detailHtml;

    /** DRAFT/READY/PUBLISHED/OFFLINE */
    private String status;

    private String mainImageUrl;

    private String sourceCategoryId;

    private Long collectTaskId;
}
