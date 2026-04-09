package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.UserCardInfoEntity;

import java.util.List;

public interface UserCardInfoService extends IService<UserCardInfoEntity> {

    /**
     * 添加用户卡组单词信息
     *
     * @param userId      userId
     * @param sourGroupId sourGroupId
     */
    void addUserCardGroup(Long userId, Long sourGroupId);

    /**
     * 获取非空
     *
     * @param cardId cardId
     * @return entity
     */
    UserCardInfoEntity getNotNullByCardId(Long cardId);

    /**
     * 添加一个用户学习卡
     *
     * @param userId userId
     * @param cardId cardId
     * @return entity
     */
    UserCardInfoEntity addUserCardInfo(Long userId, Long cardId);

    /**
     * 获取非空的用户卡
     *
     * @param id id
     * @return entity
     */
    UserCardInfoEntity getNotNullById(Long id);

    /**
     * 获取目标卡数量
     *
     * @param type 类型
     * @return 数量
     */
    List<Long> ListDelayUserCard(Integer type);

}
