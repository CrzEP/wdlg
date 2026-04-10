package com.dlg.wdlg.api;

import com.dlg.wdlg.comm.UserCardDelayEnum;
import com.dlg.wdlg.comm.UserCardStateEnum;

/**
 * 操作卡API
 */
public interface CardOptApi {

    /**
     * 操作一张卡
     *
     * @param cardId ID
     * @param statge 状态
     * @param optTime 操作时间
     */
    void optCard(Long cardId, UserCardStateEnum statge, Long optTime);

    /**
     * 延迟/更新一张卡
     *
     * @param cardId ID
     * @param delay 类型
     */
    void delayCard(Long cardId, UserCardDelayEnum delay);

}
