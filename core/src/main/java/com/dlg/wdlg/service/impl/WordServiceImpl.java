package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.WordEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.WordMapper;
import com.dlg.wdlg.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<WordEntity> findByWordOne(String word) {
        LambdaQueryWrapper<WordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordEntity::getWord,word);
        queryWrapper.orderByDesc(WordEntity::getId);
        queryWrapper.last("limit 1");
        return getOneOpt(queryWrapper);
    }

    @Override
    public WordEntity getByWordOneNonNull(String word) {
        Optional<WordEntity> entity = findByWordOne(word);
        if(entity.isPresent()){
            return entity.get();
        }
        throw new BusinessException("word not found");
    }
}
