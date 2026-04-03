package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.WordEntity;

import java.util.List;

public interface CardService extends IService<CardEntity> {

    /**
     * 保存简单公共卡：只有单词、释义
     *
     * @param wordList      wordList
     * @param cardGroupName groupName
     */
    void saveSimplePubWordCardList(List<WordEntity> wordList, String cardGroupName);

    /**
     * 保存一张公共卡
     * @param entity entity
     */
    void savePubWordCard(CardEntity entity);

    /**
     * 根据内容ID 和 组ID 查询单词卡
     * @param contentId contentId
     * @param groupId groupId
     * @return card
     */
    CardEntity findWordCardByContentIdAndGroupId(Long contentId, Long groupId);

    /**
     * 获取卡组
     * @param groupId groupId
     * @return 卡组
     */
    List<Long> listIdsByGroupId(Long groupId);

}
