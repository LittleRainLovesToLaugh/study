package com.cenyao.commerce.channel;

/**
 * 虾皮官方开放接口客户端。
 * <p>
 * 鉴权：合作方编号 / 合作方密钥 / 店铺授权令牌。
 * 正式接入时请替换为官方 SDK；禁止未授权爬取。
 * </p>
 */
public interface ShopeeClient {

    /**
     * 创建商品（官方刊登接口占位）。
     */
    String createItem(CreateItemRequest request);

    /**
     * 更新价格（官方价库同步占位）。
     */
    void updatePrice(String shopId, String itemId, String pricePayload);

    /**
     * 更新库存（官方价库同步占位）。
     */
    void updateStock(String shopId, String itemId, String stockPayload);

    record CreateItemRequest(
            String shopId,
            String title,
            String description,
            String categoryId,
            String siteRegion,
            String targetLanguage
    ) {
    }
}
