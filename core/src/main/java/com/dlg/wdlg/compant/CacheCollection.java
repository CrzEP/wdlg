package com.dlg.wdlg.compant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 缓存集合组件
 */
public class CacheCollection {

    /**
     * token 缓存
     */
    public static final Cache<String, String> TOKEN_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(100, TimeUnit.SECONDS) // 写入后10秒过期
            .maximumSize(1000) // 最多1000条
            .build();



}
