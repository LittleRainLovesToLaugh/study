package com.cenyao.commerce.pipeline;

import com.cenyao.commerce.channel.ChannelAccountService;
import com.cenyao.commerce.channel.PublishTaskService;
import com.cenyao.commerce.channel.dto.CreateChannelAccountRequest;
import com.cenyao.commerce.channel.dto.CreatePublishTaskRequest;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.entity.PublishTask;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.task.CollectTaskService;
import com.cenyao.commerce.task.dto.CreateCollectTaskRequest;
import com.cenyao.commerce.task.entity.CollectTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 一键演示流水线：采集 → 本地化 → 刊登（全部走官方接口 Stub）。
 */
@Service
@RequiredArgsConstructor
public class PipelineDemoService {

    private final CollectTaskService collectTaskService;
    private final ChannelAccountService channelAccountService;
    private final ChannelAccountMapper channelAccountMapper;
    private final PublishTaskService publishTaskService;

    public Map<String, Object> runDemo(String sourceOfferId, String targetLanguage, String siteRegion) {
        String offerId = (sourceOfferId == null || sourceOfferId.isBlank())
                ? "DEMO-" + UUID.randomUUID().toString().substring(0, 8)
                : sourceOfferId.trim();

        CreateCollectTaskRequest collectReq = new CreateCollectTaskRequest();
        collectReq.setSourceOfferId(offerId);
        collectReq.setSourceUrl("https://detail.1688.com/offer/" + offerId + ".html");
        collectReq.setPriority(10);
        CollectTask collectTask = collectTaskService.create(collectReq);
        Map<String, Object> collectResult = collectTaskService.run(collectTask.getId());

        ChannelAccount account = channelAccountMapper.selectOne(new LambdaQueryWrapper<ChannelAccount>()
                .eq(ChannelAccount::getSiteRegion, siteRegion)
                .eq(ChannelAccount::getTargetLanguage, targetLanguage)
                .eq(ChannelAccount::getStatus, 1)
                .last("limit 1"));
        if (account == null) {
            CreateChannelAccountRequest accountReq = new CreateChannelAccountRequest();
            accountReq.setShopName("演示店铺-" + siteRegion);
            accountReq.setShopId("SHOP-" + siteRegion);
            accountReq.setSiteRegion(siteRegion);
            accountReq.setTargetLanguage(targetLanguage);
            accountReq.setPartnerId("demo-partner");
            accountReq.setPartnerKey("demo-partner-key");
            accountReq.setAccessToken("demo-access-token");
            account = channelAccountService.create(accountReq);
        }

        CreatePublishTaskRequest publishReq = new CreatePublishTaskRequest();
        publishReq.setProductId(((Number) collectResult.get("productId")).longValue());
        publishReq.setChannelAccountId(account.getId());
        PublishTask publishTask = publishTaskService.create(publishReq);
        Map<String, Object> publishResult = publishTaskService.run(publishTask.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("collect", collectResult);
        result.put("channelAccountId", account.getId());
        result.put("publish", publishResult);
        return result;
    }
}
