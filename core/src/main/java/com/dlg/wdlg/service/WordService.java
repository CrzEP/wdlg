package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.WordEntity;

public interface WordService extends IService<WordEntity> {

    /**
     * 保存公共单词：唯一
     *
     * @param wordEntity wordEntity
     */
    WordEntity savePubWord(WordEntity wordEntity);

    /**
     * 根据单词找记录
     *
     * @param word 单词
     * @return WordEntity
     */
    WordEntity findByWords(String word);
}
