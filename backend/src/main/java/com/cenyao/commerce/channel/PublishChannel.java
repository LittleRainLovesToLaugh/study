package com.cenyao.commerce.channel;

/**
 * 刊登渠道抽象。一期仅实现虾皮渠道适配器；禁止擅自加其他市场。
 */
public interface PublishChannel {

    /** 渠道编码，如 SHOPEE */
    String channelCode();

    /**
     * 刊登商品到目标渠道（须使用已本地化文案）。
     */
    PublishResult publish(PublishCommand command);

    record PublishCommand(
            Long productId,
            Long channelAccountId,
            String localizedTitle,
            String localizedDetailHtml,
            String targetLanguage,
            String siteRegion
    ) {
    }

    record PublishResult(
            boolean success,
            String externalItemId,
            String message
    ) {
    }
}
