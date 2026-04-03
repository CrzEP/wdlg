package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.CardMnemonicEntity;

public interface CardMnemonicService extends IService<CardMnemonicEntity> {

    /**
     * 保存助记 句子
     *
     * @param cardId  cardId
     * @param helpRem 句子
     * @return CardMnemonicEntity
     */
    CardMnemonicEntity saveHelpRem(Long cardId, String helpRem);


    /**
     * 获取最大顺序
     *
     * @param cardId 所属卡片
     * @return CardMnemonicEntity
     */
    CardMnemonicEntity getMnemonicSortMaxByCardId(Long cardId);
}
