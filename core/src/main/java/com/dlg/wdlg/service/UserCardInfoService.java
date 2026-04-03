package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.UserCardInfoEntity;

public interface UserCardInfoService extends IService<UserCardInfoEntity> {

    /**
     * 添加用户卡组单词信息
     *
     * @param userId  userId
     * @param groupId groupId
     */
    void addUserCardGroup(Long userId, Long groupId);

    /**
     * 根据userId 和 cardId 查找
     * @param userId userId
     * @param cardId cardId
     * @return entity
     */
    UserCardInfoEntity findByUserIdAndCardId(Long userId, Long cardId);
}
