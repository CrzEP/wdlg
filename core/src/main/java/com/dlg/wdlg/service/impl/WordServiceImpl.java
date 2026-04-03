package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.WordEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.WordMapper;
import com.dlg.wdlg.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, WordEntity> implements WordService {

    @Override
    public WordEntity savePubWord(WordEntity wordEntity) {
        LambdaQueryWrapper<WordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordEntity::getWord,wordEntity.getWord());
        WordEntity entity = getOne(queryWrapper);
        if(null != entity){
            // 已存在
            return entity;
        }
        save(wordEntity);
        return wordEntity;
    }

    @Override
    public WordEntity findByWords(String word) {
        if (StringUtils.isEmpty(word)) {
            throw new BusinessException("word can not be empty");
        }
        LambdaQueryWrapper<WordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordEntity::getWord,word);
        return getOne(queryWrapper);
    }
}
