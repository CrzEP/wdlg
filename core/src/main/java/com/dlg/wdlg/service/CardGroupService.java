package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.comm.CardGroupTypeEnum;
import com.dlg.wdlg.entity.CardGroupEntity;

public interface CardGroupService extends IService<CardGroupEntity> {

    /**
     * 获取或者保存一个公开组
     *
     * @param cardGroupName 卡组名
     * @return 记录
     */
    CardGroupEntity getOrSavePubGroup(String cardGroupName);

    /**
     * 保存公共组
     *
     * @param cardGroupName 卡组名
     */
    CardGroupEntity savePubGroup(String cardGroupName);

    /**
     * 添加用户卡组
     *
     * @param userId    userId
     * @param groupName groupName
     */
    CardGroupEntity addCardGroup(Long userId, String groupName, CardGroupTypeEnum cardGroupTypeEnum);

    /**
     * 保存用户卡组
     *
     * @param userId            userId
     * @param groupName         groupName
     * @param cardGroupTypeEnum 卡组类型
     */
    CardGroupEntity saveUserGroup(Long userId, String groupName, CardGroupTypeEnum cardGroupTypeEnum);

    /**
     * 检查公共卡组
     *
     * @param pubGroupId 公共卡组
     */
    void checkPubGroup(Long pubGroupId);

    /**
     * 检查用户卡组
     *
     * @param userId  userId
     * @param groupId 卡组id
     */
    void checkUserLearnGroup(Long userId, Long groupId);

    /**
     * 检查用户卡组
     *
     * @param userId   userId
     * @param groupId  卡组id
     * @param typeEnum typeEnum
     */
    void checkUserGroup(Long userId, Long groupId, CardGroupTypeEnum typeEnum);

    /**
     * 获取用户卡组
     *
     * @param userId 用户id
     * @return 卡组
     */
    CardGroupEntity getUserLearningCardGroup(Long userId);

    /**
     * 获取非空的组
     * @param groupId
     * @return CardGroupEntity
     */
    CardGroupEntity getNotNullGroup(Long groupId);
}
