package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.MnemonicTypeEnum;
import com.dlg.wdlg.entity.CardMnemonicEntity;
import com.dlg.wdlg.mapper.CardMnemonicMapper;
import com.dlg.wdlg.service.CardMnemonicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardMnemonicServiceImpl extends ServiceImpl<CardMnemonicMapper, CardMnemonicEntity> implements CardMnemonicService {


    @Override
    public CardMnemonicEntity saveHelpRem(Long cardId, String helpRem) {
        // 检查顺序
        CardMnemonicEntity cardMnemonic = getMnemonicSortMaxByCardId(cardId);
        int sort = 1;
        if (null != cardMnemonic){
            sort = cardMnemonic.getSort() + 1 ;
        }
        CardMnemonicEntity  mnemonicEntity = new CardMnemonicEntity();
        mnemonicEntity.setSort(sort);
        mnemonicEntity.setBelongCardId(cardId);
        mnemonicEntity.setType(MnemonicTypeEnum.MEANING.getCode());
        mnemonicEntity.setContent(helpRem);
        save(mnemonicEntity);
        return mnemonicEntity;
    }

    @Override
    public CardMnemonicEntity getMnemonicSortMaxByCardId(Long cardId) {
        LambdaQueryWrapper<CardMnemonicEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardMnemonicEntity::getBelongCardId, cardId);
        queryWrapper.orderByDesc(CardMnemonicEntity::getSort);
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }
}
