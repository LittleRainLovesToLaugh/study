package com.cenyao.commerce.channel;

import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.common.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 虾皮渠道适配器（一期唯一刊登实现）。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShopeeChannelAdapter implements PublishChannel {

    public static final String CODE = "SHOPEE";

    private final ShopeeClient shopeeClient;
    private final ChannelAccountMapper channelAccountMapper;

    @Override
    public String channelCode() {
        return CODE;
    }

    @Override
    public PublishResult publish(PublishCommand command) {
        ChannelAccount account = channelAccountMapper.selectById(command.channelAccountId());
        if (account == null || account.getStatus() == null || account.getStatus() != 1) {
            throw BizException.notFound("渠道账号不存在或已停用");
        }
        if (!CODE.equalsIgnoreCase(account.getChannelCode())) {
            throw new BizException("一期仅支持虾皮渠道");
        }
        // 语言硬规则：目标语言 ≠ 简体中文时，调用方必须已完成本地化（此处再次校验标题非空）
        if (command.localizedTitle() == null || command.localizedTitle().isBlank()) {
            throw new BizException("刊登前必须完成本地化转换");
        }
        String itemId = shopeeClient.createItem(new ShopeeClient.CreateItemRequest(
                account.getShopId(),
                command.localizedTitle(),
                command.localizedDetailHtml(),
                null,
                command.siteRegion(),
                command.targetLanguage()
        ));
        log.info("虾皮刊登成功 productId={} itemId={}", command.productId(), itemId);
        return new PublishResult(true, itemId, "模拟刊登成功");
    }
}
