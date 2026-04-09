package com.dlg.wdlg.api;

import com.dlg.wdlg.comm.UserCardStateEnum;

/**
 * 操作API
 */
public interface CardNewOptApi {

    /**
     * 操作一张卡
     *
     * @param cardId ID
     * @param statge 状态
     * @param optTime 操作时间
     */
    void optCard(Long cardId, UserCardStateEnum statge,Long optTime);

    /**
     * 添加当前用户一个公共卡组
     *
     * @param groupId 卡组
     */
    void addLoginUserPubCardGroup(Long groupId);

    /**
     * 添加当前用户一个单词卡
     * 必须是公共卡组有的卡
     *
     * @param word 单词
     */
    void addLoginUserWordCard(String word);

    /**
     * 设置卡为开始记忆状态
     * @param cardId 卡ID
     */
    void setCardMemory(Long cardId);

    /**
     * 设置随机数量的卡进行记忆
     * @param count 数量，最多卡片量
     */
    void setRandomCardMemory(long count);

}
