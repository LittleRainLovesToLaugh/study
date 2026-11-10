package com.cenyao.commerce.sync;

import com.cenyao.commerce.sync.entity.SyncLog;
import com.cenyao.commerce.sync.mapper.SyncLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 同步日志写入。
 */
@Service
@RequiredArgsConstructor
public class SyncLogService {

    private final SyncLogMapper syncLogMapper;

    public void record(String syncType, String bizId, String channelCode,
                       String requestSummary, String resultSummary, boolean success, long costMs) {
        SyncLog log = new SyncLog();
        log.setSyncType(syncType);
        log.setBizId(bizId);
        log.setChannelCode(channelCode);
        log.setRequestSummary(trim(requestSummary));
        log.setResultSummary(trim(resultSummary));
        log.setSuccess(success ? 1 : 0);
        log.setCostMs((int) Math.min(costMs, Integer.MAX_VALUE));
        syncLogMapper.insert(log);
    }

    private static String trim(String value) {
        if (value == null) {
            return null;
        }
        return value.length() <= 1000 ? value : value.substring(0, 1000);
    }
}
