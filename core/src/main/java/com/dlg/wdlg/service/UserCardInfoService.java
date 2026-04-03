package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.UserCardInfoEntity;

public interface UserCardInfoService extends IService<UserCardInfoEntity> {

    /**
     * 添加用户卡组单词信息
     *
     * @param userId  userId
     * @param sourGroupId sourGroupId
     */
    void addUserCardGroup(Long userId, Long sourGroupId);

    /**
     * 获取非空
     * @param cardId cardId
     * @return entity
     */
    UserCardInfoEntity getNotNullByCardId(Long cardId);

    /**
     * 添加一个用户学习卡
     * @param userId userId
     * @param cardId cardId
     * @return entity
     */
    UserCardInfoEntity addUserCardInfo(Long userId, Long cardId);
}
