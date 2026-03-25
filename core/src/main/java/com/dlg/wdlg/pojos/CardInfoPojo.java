package com.dlg.wdlg.pojos;

import com.dlg.wdlg.entity.CardMnemonicEntity;
import lombok.Data;

import java.util.List;

@Data
public class CardInfoPojo {

    /**
     * 卡片ID
     */
    private Long cardId;

    /**
     * 单词ID
     */
    private Long wordId;

    /**
     * 单词/词组
     */
    private String word;

    /**
     * 释意
     */
    private String paraphrase;

    /**
     * 助记
     */
    private List<CardMnemonicEntity> cardMnemonicList;

}
