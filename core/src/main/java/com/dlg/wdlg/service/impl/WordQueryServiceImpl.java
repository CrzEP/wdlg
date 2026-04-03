package com.dlg.wdlg.service.impl;

import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.WordEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.service.CardGroupService;
import com.dlg.wdlg.service.CardService;
import com.dlg.wdlg.service.WordQueryService;
import com.dlg.wdlg.service.WordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WordQueryServiceImpl implements WordQueryService {

    @Resource
    CardService cardService;
    @Resource
    WordService wordService;
    @Resource
    CardGroupService cardGroupService;

    @Override
    public CardEntity getCardByWordAndGroupId(String word,Long groupId) {
        if (StringUtils.isEmpty(word) || null == groupId){
            throw new BusinessException("method params not be null");
        }
        WordEntity words = wordService.findByWords(word);
        if (null == words){
            return null;
        }
        CardGroupEntity cardGroup = cardGroupService.getById(groupId);
        if (null == cardGroup){
            return null;
        }
        return cardService.findWordCardByContentIdAndGroupId(words.getId(),cardGroup.getId());
    }

}
