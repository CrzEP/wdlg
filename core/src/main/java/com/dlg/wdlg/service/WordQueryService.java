package com.dlg.wdlg.service;

import com.dlg.wdlg.entity.CardEntity;

public interface WordQueryService {

    /**
     * 通过单词和分组id获取卡片记录
     *
     * @return 卡片
     */
    CardEntity getCardByWordAndGroupId(String word, Long groupId);

}
