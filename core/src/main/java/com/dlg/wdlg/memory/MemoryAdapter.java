package com.dlg.wdlg.memory;

import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;

/**
 * 记忆算法接口适配器，所有记忆算法使用都需要实现这个接口以使用
 */
public interface MemoryAdapter {

    /**
     * 复习
     *
     * @param entity     卡片信息
     * @param state      状态
     * @param memoryTime 记忆操作时间
     * @return 卡片、日志
     */
    CardUserAndLogInfo review(UserCardInfoEntity entity, UserCardStateEnum state, long memoryTime);

}
