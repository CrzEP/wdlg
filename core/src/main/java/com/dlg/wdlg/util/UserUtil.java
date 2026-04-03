package com.dlg.wdlg.util;

import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.UserEntity;
import com.dlg.wdlg.exception.BusinessException;

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

        return null;
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static UserEntity getUserNotNull() {
        String token = ServletRequestUtil.getTokenWithNotNull();
        UserEntity userEntity = CacheCollection.TOKEN_CACHE.getIfPresent(token);
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

}
