package com.cenyao.commerce.channel;

import com.cenyao.commerce.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 虾皮官方接口 Mock/Stub。
 * <p>
 * 【待接官方 SDK】不发起真实请求；仅供自动刊登 / 价库同步流程联调。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MockShopeeClient implements ShopeeClient {

    private final AppProperties appProperties;

    @Override
    public String createItem(CreateItemRequest request) {
        String itemId = "MOCK-SHOPEE-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("【Stub】虾皮创建商品 shopId={} lang={} site={} partnerId={} → itemId={}",
                request.shopId(), request.targetLanguage(), request.siteRegion(),
                appProperties.getShopee().getPartnerId(), itemId);
        return itemId;
    }

    @Override
    public void updatePrice(String shopId, String itemId, String pricePayload) {
        log.info("【Stub】虾皮更新价格 shopId={} itemId={}", shopId, itemId);
    }

    @Override
    public void updateStock(String shopId, String itemId, String stockPayload) {
        log.info("【Stub】虾皮更新库存 shopId={} itemId={}", shopId, itemId);
    }
}
