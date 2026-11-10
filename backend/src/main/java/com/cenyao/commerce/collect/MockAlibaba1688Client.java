package com.cenyao.commerce.collect;

import com.cenyao.commerce.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 1688 官方接口 Mock/Stub。
 * <p>
 * 【待接官方 SDK】当前返回本地模拟数据，便于联调采集入库流程。
 * 不发起真实 HTTP；不实现任何爬虫逻辑。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MockAlibaba1688Client implements Alibaba1688Client {

    private final AppProperties appProperties;

    @Override
    public OfferDetail fetchOfferDetail(String offerId) {
        log.info("【Stub】1688 拉取货源 offerId={}，appKey 占位={}，待接官方 SDK",
                offerId, appProperties.getAlibaba1688().getAppKey());
        return new OfferDetail(
                offerId,
                "【模拟】示例商品-" + offerId,
                "<p>模拟详情，源语言为简体中文</p>",
                "https://example.invalid/mock/" + offerId + ".jpg",
                "mock-category-001",
                "mock-raw-summary"
        );
    }
}
