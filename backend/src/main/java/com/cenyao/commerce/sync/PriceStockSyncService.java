package com.cenyao.commerce.sync;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.channel.ShopeeChannelAdapter;
import com.cenyao.commerce.channel.ShopeeClient;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.entity.PublishTask;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.channel.mapper.PublishTaskMapper;
import com.cenyao.commerce.common.RedisLockService;
import com.cenyao.commerce.product.entity.ProductSku;
import com.cenyao.commerce.product.mapper.ProductSkuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价库同步：拉源价库（Stub）→ 推虾皮官方接口（Stub）。
 * <p>
 * 正式环境由 XXL-JOB 调度；本地可用 REST / Spring 定时入口触发。
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceStockSyncService {

    private final PublishTaskMapper publishTaskMapper;
    private final ChannelAccountMapper channelAccountMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ShopeeClient shopeeClient;
    private final SyncLogService syncLogService;
    private final RedisLockService redisLockService;

    public Map<String, Object> syncAllPublished() {
        return redisLockService.executeWithLock("lock:sync:price-stock", Duration.ofMinutes(5), this::doSync);
    }

    private Map<String, Object> doSync() {
        List<PublishTask> published = publishTaskMapper.selectList(new LambdaQueryWrapper<PublishTask>()
                .eq(PublishTask::getStatus, "SUCCESS")
                .isNotNull(PublishTask::getExternalItemId));

        int priceOk = 0;
        int stockOk = 0;
        int failed = 0;

        for (PublishTask task : published) {
            ChannelAccount account = channelAccountMapper.selectById(task.getChannelAccountId());
            if (account == null) {
                failed++;
                continue;
            }
            List<ProductSku> skus = productSkuMapper.selectList(new LambdaQueryWrapper<ProductSku>()
                    .eq(ProductSku::getProductId, task.getProductId()));
            String pricePayload = skus.isEmpty() ? "[]" : skus.get(0).getPrice().toPlainString();
            String stockPayload = skus.isEmpty() ? "0" : String.valueOf(skus.get(0).getStock());

            long t0 = System.currentTimeMillis();
            try {
                shopeeClient.updatePrice(account.getShopId(), task.getExternalItemId(), pricePayload);
                syncLogService.record("PRICE", String.valueOf(task.getId()), ShopeeChannelAdapter.CODE,
                        "itemId=" + task.getExternalItemId(), "price=" + pricePayload, true,
                        System.currentTimeMillis() - t0);
                priceOk++;

                long t1 = System.currentTimeMillis();
                shopeeClient.updateStock(account.getShopId(), task.getExternalItemId(), stockPayload);
                syncLogService.record("STOCK", String.valueOf(task.getId()), ShopeeChannelAdapter.CODE,
                        "itemId=" + task.getExternalItemId(), "stock=" + stockPayload, true,
                        System.currentTimeMillis() - t1);
                stockOk++;
            } catch (Exception ex) {
                failed++;
                syncLogService.record("PRICE", String.valueOf(task.getId()), ShopeeChannelAdapter.CODE,
                        "itemId=" + task.getExternalItemId(), ex.getMessage(), false,
                        System.currentTimeMillis() - t0);
                log.warn("价库同步失败 taskId={}", task.getId(), ex);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("publishedCount", published.size());
        result.put("priceOk", priceOk);
        result.put("stockOk", stockOk);
        result.put("failed", failed);
        return result;
    }
}
