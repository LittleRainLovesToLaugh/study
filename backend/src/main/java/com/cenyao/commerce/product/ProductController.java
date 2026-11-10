package com.cenyao.commerce.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cenyao.commerce.common.ApiResponse;
import com.cenyao.commerce.product.entity.Product;
import com.cenyao.commerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库查询骨架（完整增删改查留待后续阶段）。
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;

    @GetMapping
    public ApiResponse<Page<Product>> page(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size) {
        Page<Product> result = productMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Product>().orderByDesc(Product::getId));
        return ApiResponse.ok(result);
    }
}
