package com.dlg.wdlg.api;

import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.pojos.CardInfoPojo;

/**
 * 操作API
 */
public interface OptApi {

    /**
     * 获取下一张需要记忆的卡
     * @return 卡
     */
    CardInfoPojo getNextCard();

    /**
     * 操作一张卡
     *
     * @param cardId ID
     * @param statge 状态
     */
    void optCard(Long cardId, UserCardStateEnum statge);

}
