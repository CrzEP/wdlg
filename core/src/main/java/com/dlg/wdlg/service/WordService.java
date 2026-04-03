package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.WordEntity;

import java.util.Optional;

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
    Optional<WordEntity> findByWordOne(String word);

    /**
     * 根据单词找记录,非空
     *
     * @param word 单词
     * @return WordEntity
     */
    WordEntity getByWordOneNonNull(String word);

}
