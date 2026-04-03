package com.dlg.wdlg.compant;

import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.entity.UserEntity;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 缓存集合组件
 */
public class CacheCollection {

    /**
     * token 缓存
     */
    public static final Cache<String, UserEntity> TOKEN_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS) // 写入后7天过期
            .maximumSize(100) // 最多100条
            .build();

    /**
     * 用户卡片缓存
     */
    public static final Cache<Long, LinkedHashMap<String, UserCardInfoEntity>> USER_CARD_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES) // 写入后7天过期
            .maximumSize(1000) // 最多1000条
            .build();



}
