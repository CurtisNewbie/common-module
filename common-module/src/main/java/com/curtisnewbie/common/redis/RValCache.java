package com.curtisnewbie.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * RedisTemplate based simple k-v cache.
 * <p>
 * Useful when we are constantly doing something like:
 * <pre>
 *     {@code
 *          redisTemplate.opsForValue().get(keyPattern.format(key));
 *
 *          redisTemplate.opsForValue().set(keyPattern.format(key), val, ttl);
 *     }
 * </pre>
 * <p>
 * Redis key is concatenated in this way: {@code name + key}.
 *
 * @author yongj.zhuang
 */
public final class RValCache<T> {

    private Supplier<RedisTemplate> _redisTemplateSupplier;
    private RedisTemplate _redisTemplate;
    private volatile boolean _init;

    private final String name;

    public RValCache(Supplier<RedisTemplate> redisTemplateSupplier, String name) {
        this._init = false;
        this._redisTemplateSupplier = redisTemplateSupplier;
        this.name = name;
    }

    public T get(String key) {
        return (T) getRedisTemplate().opsForValue().get(this.name + key);
    }

    public void set(String key, T t, Duration ttl) {
        getRedisTemplate().opsForValue().set(this.name + key, t, ttl);
    }

    private RedisTemplate getRedisTemplate() {
        if (_init) {
            return this._redisTemplate;
        }

        synchronized (this) {
            if (_init) {
                return this._redisTemplate;
            }

            this._redisTemplate = this._redisTemplateSupplier.get();
            return this._redisTemplate;
        }
    }

}
