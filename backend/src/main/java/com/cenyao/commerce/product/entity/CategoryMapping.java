package com.cenyao.commerce.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cenyao.commerce.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 1688 ↔ 虾皮类目映射。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("category_mapping")
public class CategoryMapping extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String sourceCategoryId;

    private String sourceCategoryName;

    private String shopeeCategoryId;

    private String shopeeCategoryName;

    private String mappingRule;

    private Integer status;
}
