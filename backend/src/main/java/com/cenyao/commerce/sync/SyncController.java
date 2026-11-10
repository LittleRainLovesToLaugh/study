package com.cenyao.commerce.sync;

import com.cenyao.commerce.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 价库同步手动触发入口（XXL-JOB / 定时任务亦可调用同一服务）。
 */
@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SyncController {

    private final PriceStockSyncService priceStockSyncService;

    @PostMapping("/price-stock")
    public ApiResponse<Map<String, Object>> syncPriceStock() {
        return ApiResponse.ok(priceStockSyncService.syncAllPublished());
    }
}
