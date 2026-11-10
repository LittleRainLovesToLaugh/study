package com.cenyao.commerce.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 价库同步调度占位。
 * <p>
 * 【XXL-JOB】正式接入时在此对应 JobHandler（如 commercePriceStockSync），
 * 由 XXL-JOB 控制台配置 cron；当前用 Spring 定时便于本地联调，默认关闭（需配置开启）。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PriceStockSyncJob {

    private final PriceStockSyncService priceStockSyncService;

    /**
     * 本地占位：每小时整点尝试一次；无已刊登商品时几乎无副作用。
     * 生产请改走 XXL-JOB，并关闭或调整本定时。
     */
    @Scheduled(cron = "${app.sync.price-stock-cron:0 0 * * * ?}")
    public void scheduledSync() {
        try {
            var result = priceStockSyncService.syncAllPublished();
            log.info("【调度】价库同步完成 {}", result);
        } catch (Exception ex) {
            log.warn("【调度】价库同步跳过或失败：{}", ex.getMessage());
        }
    }

    /**
     * XXL-JOB 接入点说明（待接官方执行器）：
     * <pre>
     * &#64;XxlJob("commercePriceStockSync")
     * public void xxlJobHandler() { priceStockSyncService.syncAllPublished(); }
     * </pre>
     */
    public void xxlJobHandlerPlaceholder() {
        priceStockSyncService.syncAllPublished();
    }
}
