package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.CardTypeEnum;
import com.dlg.wdlg.comm.GConst;
import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.WordEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.CardMapper;
import com.dlg.wdlg.service.CardGroupService;
import com.dlg.wdlg.service.CardService;
import com.dlg.wdlg.service.WordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, CardEntity> implements CardService {

    @Resource
    WordService wordService;
    @Resource
    CardGroupService groupService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSimplePubWordCardList(List<WordEntity> wordList, String cardGroupName) {
        List<WordEntity> savedList = new ArrayList<>();
        CardGroupEntity group = groupService.getOrSavePubGroup(cardGroupName);
        for (WordEntity wordEntity : wordList) {
            // 保存单词，false代表已存在
            wordEntity = wordService.savePubWord(wordEntity);
            savedList.add(wordEntity);
            saveSimplePubWordCard(wordEntity, group.getId());
            log.info("简单单词卡片 {} 保存成功，", wordEntity.getWord());
        }
        logSaveInfo(wordList, savedList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePubWordCard(CardEntity card) {
        CardEntity cardEntity = findWordCardByContentIdAndGroupId(card.getContentId(),card.getBelongGroupId());
        if (cardEntity == null) {
            save(card);
            return;
        }
        throw new BusinessException("重复的卡片");
    }

    @Override
    public CardEntity findWordCardByContentIdAndGroupId(Long contentId, Long groupId) {
        LambdaQueryWrapper<CardEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardEntity::getBelongGroupId, groupId);
        queryWrapper.eq(CardEntity::getContentId, contentId);
        return getOne(queryWrapper);
    }

    @Override
    public List<Long> listIdsByGroupId(Long id) {
        return this.lambdaQuery()
                .eq(CardEntity::getBelongGroupId, id)
                .select(CardEntity::getId)
                .list()
                .stream()
                .map(CardEntity::getId)
                .toList();
    }

    @Override
    public CardEntity findWordCardByContentIdAndType(Long id, CardTypeEnum type) {
        LambdaQueryWrapper<CardEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardEntity::getContentId, id);
        queryWrapper.eq(CardEntity::getType, type.getCode());
        return getOne(queryWrapper);

    }

    @Override
    public CardEntity findOneWordCard(String word) {
        Optional<WordEntity> optionalWord = wordService.findByWordOne(word);
        if (optionalWord.isEmpty()) {
            throw new BusinessException("not word : " + word + "public card found");
        }
        WordEntity wordEntity = optionalWord.get();
        return findWordCardByContentIdAndType(wordEntity.getId(), CardTypeEnum.WORD);
    }

    /**
     * 打印保存信息
     *
     * @param wordList  wordList
     * @param savedList savedList
     */
    private static void logSaveInfo(List<WordEntity> wordList, List<WordEntity> savedList) {
        int size = wordList.size();
        int successSize = savedList.size();
        int fail = size - successSize;
        log.info("批次单词卡片保存成功，总数：{} 成功数: {} 已存在/失败数：{}",
                size, successSize, fail);
    }

    /**
     * 保存一张卡
     *
     * @param wordEntity wordEntity
     * @param groupId    组id
     */
    private void saveSimplePubWordCard(WordEntity wordEntity, Long groupId) {
        CardEntity card = new CardEntity();
        card.setType(CardTypeEnum.WORD.getCode());
        card.setContentId(wordEntity.getId());
        card.setBelongUserId(GConst.PUBLIC_USER_ID);
        card.setBelongGroupId(groupId);
        save(card);
    }

}
