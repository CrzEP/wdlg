package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.CardGroupEntity;

public interface CardGroupService extends IService<CardGroupEntity> {

    /**
     * 获取或者保存一个公开组
     * @param cardGroupName 卡组名
     * @return 记录
     */
    CardGroupEntity getOrSavePubGroup(String cardGroupName);

    /**
     * 保存公共组
     * @param cardGroupName 卡组名
     */
    CardGroupEntity savePubGroup(String cardGroupName);

    /**
     * 保存用户卡组,默认公共卡组名
     * @param userId userId
     * @param groupId groupId
     */
    void saveUserCardGroupFromPubCardGroup(Long userId, Long groupId);
}
