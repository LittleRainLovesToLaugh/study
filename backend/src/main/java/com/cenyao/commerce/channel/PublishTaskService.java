package com.cenyao.commerce.channel;

import com.cenyao.commerce.channel.dto.CreatePublishTaskRequest;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.entity.PublishTask;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.channel.mapper.PublishTaskMapper;
import com.cenyao.commerce.common.BizException;
import com.cenyao.commerce.common.RedisLockService;
import com.cenyao.commerce.localization.LocalizationService;
import com.cenyao.commerce.product.entity.Product;
import com.cenyao.commerce.product.mapper.ProductMapper;
import com.cenyao.commerce.sync.SyncLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动刊登队列：本地化转换 → 虾皮渠道适配器 → 同步日志。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PublishTaskService {

    private final PublishTaskMapper publishTaskMapper;
    private final ChannelAccountMapper channelAccountMapper;
    private final ProductMapper productMapper;
    private final LocalizationService localizationService;
    private final ShopeeChannelAdapter shopeeChannelAdapter;
    private final SyncLogService syncLogService;
    private final RedisLockService redisLockService;

    public PublishTask create(CreatePublishTaskRequest request) {
        Product product = productMapper.selectById(request.getProductId());
        if (product == null) {
            throw BizException.notFound("商品不存在");
        }
        ChannelAccount account = channelAccountMapper.selectById(request.getChannelAccountId());
        if (account == null || account.getStatus() == null || account.getStatus() != 1) {
            throw BizException.notFound("渠道账号不存在或已停用");
        }
        if (!ShopeeChannelAdapter.CODE.equalsIgnoreCase(account.getChannelCode())) {
            throw new BizException("一期仅支持虾皮渠道");
        }

        PublishTask task = new PublishTask();
        task.setProductId(product.getId());
        task.setChannelAccountId(account.getId());
        task.setChannelCode(account.getChannelCode());
        task.setTargetLanguage(account.getTargetLanguage());
        task.setStatus("PENDING");
        publishTaskMapper.insert(task);
        return task;
    }

    @Transactional
    public Map<String, Object> run(Long taskId) {
        return redisLockService.executeWithLock("lock:publish:" + taskId, Duration.ofMinutes(3), () -> doRun(taskId));
    }

    private Map<String, Object> doRun(Long taskId) {
        long start = System.currentTimeMillis();
        PublishTask task = publishTaskMapper.selectById(taskId);
        if (task == null) {
            throw BizException.notFound("刊登任务不存在");
        }
        Product product = productMapper.selectById(task.getProductId());
        if (product == null) {
            throw BizException.notFound("商品不存在");
        }
        ChannelAccount account = channelAccountMapper.selectById(task.getChannelAccountId());
        if (account == null) {
            throw BizException.notFound("渠道账号不存在");
        }

        try {
            // 1) 本地化
            task.setStatus("LOCALIZING");
            publishTaskMapper.updateById(task);
            long localizeStart = System.currentTimeMillis();
            LocalizationService.LocalizedContent localized = localizationService.localize(
                    product.getTitle(), product.getDetailHtml(), account.getTargetLanguage());
            task.setLocalizedTitle(localized.title());
            syncLogService.record("LOCALIZE", String.valueOf(task.getId()), account.getChannelCode(),
                    "lang=" + account.getTargetLanguage() + ",productId=" + product.getId(),
                    "translated=" + localized.translated(),
                    true, System.currentTimeMillis() - localizeStart);

            // 2) 刊登
            task.setStatus("PUBLISHING");
            publishTaskMapper.updateById(task);
            PublishChannel.PublishResult result = shopeeChannelAdapter.publish(new PublishChannel.PublishCommand(
                    product.getId(),
                    account.getId(),
                    localized.title(),
                    localized.detailHtml(),
                    account.getTargetLanguage(),
                    account.getSiteRegion()
            ));

            task.setStatus("SUCCESS");
            task.setExternalItemId(result.externalItemId());
            task.setErrorMessage(null);
            publishTaskMapper.updateById(task);

            product.setStatus("PUBLISHED");
            productMapper.updateById(product);

            syncLogService.record("PUBLISH", String.valueOf(task.getId()), account.getChannelCode(),
                    "productId=" + product.getId() + ",shop=" + account.getShopName(),
                    "itemId=" + result.externalItemId(),
                    true, System.currentTimeMillis() - start);

            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("status", task.getStatus());
            map.put("externalItemId", task.getExternalItemId());
            map.put("localizedTitle", task.getLocalizedTitle());
            map.put("translated", localized.translated());
            return map;
        } catch (Exception ex) {
            task.setStatus("FAILED");
            task.setErrorMessage(ex.getMessage());
            publishTaskMapper.updateById(task);
            syncLogService.record("PUBLISH", String.valueOf(task.getId()),
                    account.getChannelCode(),
                    "productId=" + product.getId(),
                    ex.getMessage(),
                    false, System.currentTimeMillis() - start);
            throw ex instanceof BizException ? (BizException) ex : new BizException("刊登失败：" + ex.getMessage());
        }
    }
}
