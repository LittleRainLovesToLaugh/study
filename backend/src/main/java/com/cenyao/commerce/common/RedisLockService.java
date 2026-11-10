package com.cenyao.commerce.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Redis 分布式锁（任务幂等 / 防并发）。
 */
@Component
@RequiredArgsConstructor
public class RedisLockService {

    private final StringRedisTemplate stringRedisTemplate;

    public <T> T executeWithLock(String key, Duration ttl, Supplier<T> action) {
        String token = UUID.randomUUID().toString();
        Boolean ok = stringRedisTemplate.opsForValue().setIfAbsent(key, token, ttl);
        if (!Boolean.TRUE.equals(ok)) {
            throw new BizException("任务正在处理中，请稍后重试");
        }
        try {
            return action.get();
        } finally {
            String current = stringRedisTemplate.opsForValue().get(key);
            if (token.equals(current)) {
                stringRedisTemplate.delete(key);
            }
        }
    }

    public void executeWithLock(String key, Duration ttl, Runnable action) {
        executeWithLock(key, ttl, () -> {
            action.run();
            return null;
        });
    }
}
