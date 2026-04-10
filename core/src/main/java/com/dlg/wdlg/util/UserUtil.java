package com.dlg.wdlg.util;

import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.UserEntity;
import com.dlg.wdlg.exception.BusinessException;

import java.util.Map;

/**
 * 用户工具
 */
public class UserUtil {

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static UserEntity getUser() {
        String token = ServletRequestUtil.getTokenWithNotNull();
        return CacheCollection.TOKEN_CACHE.getIfPresent(token);
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static UserEntity getUserNotNull() {
        UserEntity userEntity = getUser();
        if (null == userEntity) {
            throw new BusinessException("user not found");
        }
        return userEntity;
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static Long getUserIdNotNull() {
        UserEntity user = getUserNotNull();
        return user.getId();
    }

    /**
     * 获取当前用户map
     *
     * @return 当前用户学习组
     */
    public static Map<String, Object> getUserInfoMap() {
        String token = ServletRequestUtil.getTokenWithNotNull();
        Map<String,Object> map = CacheCollection.TOKEN_USER_MAP.getIfPresent(token);
        if (null == map) {
            throw new BusinessException("userInfoMap not found");
        }
        return map;
    }

    /**
     * 获取当前用户，用户学习组ID
     *
     * @return 当前用户学习组
     */
    public static CardGroupEntity getUserLearnGroupEntity() {
        Map<String,Object> userInfoMap = getUserInfoMap();
        return (CardGroupEntity) userInfoMap.get(CacheCollection.USER_GROUP_CACHE_NAME);
    }

    /**
     * 获取当前用户，用户学习组ID
     *
     * @return 当前用户学习组
     */
    public static CardGroupEntity getUserLearnGroupEntityWithNonull() {
        CardGroupEntity entity = getUserLearnGroupEntity();
        if (null == entity) {
            throw new BusinessException("group entity is null");
        }
        return entity;
    }

}
