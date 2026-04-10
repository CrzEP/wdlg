package com.dlg.wdlg.api;

import com.dlg.wdlg.entity.UserCardInfoEntity;

import java.util.List;

/**
 * 卡查询API
 */
public interface CardQueryApi {

    /**
     * 查询今天记忆的卡信息
     * @param max 最多卡数量
     * @return 卡集合
     */
    List<UserCardInfoEntity> findTodayMemoryCardInfoList(Long max);

}
