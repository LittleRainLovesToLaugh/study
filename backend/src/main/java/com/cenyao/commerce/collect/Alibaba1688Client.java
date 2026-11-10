package com.cenyao.commerce.collect;

/**
 * 1688 官方开放平台客户端接口。
 * <p>
 * 仅允许对接官方开放接口；禁止未授权爬取 / 绕过反爬。
 * 正式接入时请替换为官方 SDK 实现。
 * </p>
 */
public interface Alibaba1688Client {

    /**
     * 按货源编号拉取商品详情（官方接口）。
     *
     * @param offerId 1688 货源编号
     * @return 标准化商品草稿（模拟或官方响应映射）
     */
    OfferDetail fetchOfferDetail(String offerId);

    /**
     * 1688 商品详情草稿（字段待对齐官方 SDK）。
     */
    record OfferDetail(
            String offerId,
            String title,
            String detailHtml,
            String mainImageUrl,
            String categoryId,
            String rawSummary
    ) {
    }
}
