package com.cenyao.commerce.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.collect.Alibaba1688Client;
import com.cenyao.commerce.common.BizException;
import com.cenyao.commerce.common.RedisLockService;
import com.cenyao.commerce.product.entity.Product;
import com.cenyao.commerce.product.entity.ProductImage;
import com.cenyao.commerce.product.entity.ProductSku;
import com.cenyao.commerce.product.mapper.ProductImageMapper;
import com.cenyao.commerce.product.mapper.ProductMapper;
import com.cenyao.commerce.product.mapper.ProductSkuMapper;
import com.cenyao.commerce.security.LoginUser;
import com.cenyao.commerce.sync.SyncLogService;
import com.cenyao.commerce.task.dto.CreateCollectTaskRequest;
import com.cenyao.commerce.task.entity.CollectTask;
import com.cenyao.commerce.task.mapper.CollectTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 采集任务编排：创建 → 1688 官方客户端 Stub → 入库商品/规格/图片。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CollectTaskService {

    private final CollectTaskMapper collectTaskMapper;
    private final Alibaba1688Client alibaba1688Client;
    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ProductImageMapper productImageMapper;
    private final SyncLogService syncLogService;
    private final RedisLockService redisLockService;

    public CollectTask create(CreateCollectTaskRequest request) {
        CollectTask task = new CollectTask();
        task.setSourceOfferId(request.getSourceOfferId().trim());
        task.setSourceUrl(request.getSourceUrl());
        task.setPriority(request.getPriority() == null ? 0 : request.getPriority());
        task.setStatus("PENDING");
        task.setRetryCount(0);
        task.setOperatorId(currentUserId());
        collectTaskMapper.insert(task);
        return task;
    }

    @Transactional
    public Map<String, Object> run(Long taskId) {
        return redisLockService.executeWithLock("lock:collect:" + taskId, Duration.ofMinutes(2), () -> doRun(taskId));
    }

    private Map<String, Object> doRun(Long taskId) {
        long start = System.currentTimeMillis();
        CollectTask task = collectTaskMapper.selectById(taskId);
        if (task == null) {
            throw BizException.notFound("采集任务不存在");
        }
        if ("RUNNING".equals(task.getStatus())) {
            throw new BizException("任务正在执行中");
        }
        task.setStatus("RUNNING");
        task.setErrorMessage(null);
        collectTaskMapper.updateById(task);

        try {
            Alibaba1688Client.OfferDetail offer = alibaba1688Client.fetchOfferDetail(task.getSourceOfferId());
            Product product = upsertProduct(task, offer);
            ensureSkuAndImage(product, offer);

            task.setStatus("SUCCESS");
            collectTaskMapper.updateById(task);

            syncLogService.record("COLLECT", String.valueOf(task.getId()), null,
                    "offerId=" + task.getSourceOfferId(),
                    "productId=" + product.getId(),
                    true, System.currentTimeMillis() - start);

            Map<String, Object> result = new HashMap<>();
            result.put("taskId", task.getId());
            result.put("status", task.getStatus());
            result.put("productId", product.getId());
            result.put("sourceOfferId", product.getSourceOfferId());
            return result;
        } catch (Exception ex) {
            task.setStatus("FAILED");
            task.setRetryCount(task.getRetryCount() == null ? 1 : task.getRetryCount() + 1);
            task.setErrorMessage(ex.getMessage());
            collectTaskMapper.updateById(task);
            syncLogService.record("COLLECT", String.valueOf(task.getId()), null,
                    "offerId=" + task.getSourceOfferId(),
                    ex.getMessage(),
                    false, System.currentTimeMillis() - start);
            throw ex instanceof BizException ? (BizException) ex : new BizException("采集失败：" + ex.getMessage());
        }
    }

    private Product upsertProduct(CollectTask task, Alibaba1688Client.OfferDetail offer) {
        Product existing = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getSourceOfferId, offer.offerId()));
        if (existing == null) {
            Product product = new Product();
            product.setSourceOfferId(offer.offerId());
            product.setTitle(offer.title());
            product.setDetailHtml(offer.detailHtml());
            product.setMainImageUrl(offer.mainImageUrl());
            product.setSourceCategoryId(offer.categoryId());
            product.setCollectTaskId(task.getId());
            product.setStatus("READY");
            productMapper.insert(product);
            return product;
        }
        existing.setTitle(offer.title());
        existing.setDetailHtml(offer.detailHtml());
        existing.setMainImageUrl(offer.mainImageUrl());
        existing.setSourceCategoryId(offer.categoryId());
        existing.setCollectTaskId(task.getId());
        existing.setStatus("READY");
        productMapper.updateById(existing);
        return existing;
    }

    private void ensureSkuAndImage(Product product, Alibaba1688Client.OfferDetail offer) {
        Long skuCount = productSkuMapper.selectCount(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, product.getId()));
        if (skuCount == null || skuCount == 0) {
            ProductSku sku = new ProductSku();
            sku.setProductId(product.getId());
            sku.setSkuCode("MOCK-" + offer.offerId() + "-DEFAULT");
            sku.setSpecJson("{\"规格\":\"默认\"}");
            sku.setPrice(new BigDecimal("19.9000"));
            sku.setStock(100);
            sku.setWeightGram(500);
            productSkuMapper.insert(sku);
        }

        Long imageCount = productImageMapper.selectCount(new LambdaQueryWrapper<ProductImage>()
                .eq(ProductImage::getProductId, product.getId()));
        if (imageCount == null || imageCount == 0) {
            ProductImage image = new ProductImage();
            image.setProductId(product.getId());
            image.setOriginUrl(offer.mainImageUrl());
            image.setObjectKey(null);
            image.setSortOrder(0);
            image.setUsageType("MAIN");
            productImageMapper.insert(image);
        }
    }

    private Long currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUser loginUser) {
            return loginUser.userId();
        }
        return null;
    }
}
