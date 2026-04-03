package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.CardRelativeEntity;
import com.dlg.wdlg.mapper.CardRelativeMapper;
import com.dlg.wdlg.service.CardRelativeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardRelativeServiceImpl extends ServiceImpl<CardRelativeMapper, CardRelativeEntity> implements CardRelativeService {

    /**
     * 默认关联tag
     */
    private final String DEF_RELATIVE_TAG = "";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWordRel(Long sour, Long dest) {
        LambdaQueryWrapper<CardRelativeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardRelativeEntity::getBelongCardId,sour);
        queryWrapper.eq(CardRelativeEntity::getRelativeCardId,dest);
        queryWrapper.eq(CardRelativeEntity::getTag,DEF_RELATIVE_TAG);
        CardRelativeEntity entity = getOne(queryWrapper);
        // 无需重复保存
        if (entity != null) {
            return;
        }
        CardRelativeEntity relativeEntity = new CardRelativeEntity();
        relativeEntity.setBelongCardId(sour);
        relativeEntity.setRelativeCardId(dest);
        relativeEntity.setTag(DEF_RELATIVE_TAG);
        save(relativeEntity);
    }

}
