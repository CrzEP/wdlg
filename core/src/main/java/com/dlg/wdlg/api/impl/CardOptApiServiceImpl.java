package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.CardOptApi;
import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.memory.MemoryAdapter;
import com.dlg.wdlg.pojos.CardInfoPojo;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;
import com.dlg.wdlg.service.*;
import com.dlg.wdlg.util.UserUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

@Service
@Slf4j
public class CardOptApiServiceImpl implements CardOptApi {

    @Resource
    CardGroupService cardGroupService;
    @Resource
    UserCardInfoService userCardInfoService;
    @Resource
    CardMemoryLogService cardMemoryLogService;
    @Resource
    MemoryAdapter memoryAdapter;

    private final Cache<Long, LinkedHashMap<String, UserCardInfoEntity>> userCardCache = CacheCollection.USER_CARD_CACHE;
    @Autowired
    private WordService wordService;
    @Autowired
    private CardService cardService;

    @Override
    public CardInfoPojo getNextCard() {
        // 计算

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void optCard(Long cardId, UserCardStateEnum statge, Long optTime) {
        UserCardInfoEntity cardInfoEntity = userCardInfoService.getNotNullByCardId(cardId);
        // 复习
        if (optTime == null) {
            optTime = System.currentTimeMillis();
        }
        CardUserAndLogInfo userAndLogInfo = memoryAdapter.review(cardInfoEntity, statge, optTime);
        updateCardUserAndLogInfo(userAndLogInfo);
    }

    /**
     * 更新
     *
     * @param userAndLogInfo userAndLogInfo
     */
    public void updateCardUserAndLogInfo(CardUserAndLogInfo userAndLogInfo) {
        userCardInfoService.updateById(userAndLogInfo.getUserCardInfoEntity());
        cardMemoryLogService.save(userAndLogInfo.getMemoryLogEntity());
    }

    @Override
    public void addLoginUserPubCardGroup(Long pubGroupId) {
        Long userId = UserUtil.getUserIdNotNull();
        // 添加用户卡组单词信息
        userCardInfoService.addUserCardGroup(userId, pubGroupId);
    }

    @Override
    public void addLoginUserWordCard(String word) {
        CardEntity cardEntity = cardService.findOneWordCard(word);
        if (cardEntity == null) {
            throw new BusinessException("not word : " + word + "public card found");
        }
        Long userId = UserUtil.getUserIdNotNull();
        userCardInfoService.addUserCardInfo(userId, cardEntity.getId());
        log.info("添加单词卡 {} 成功", word);
    }

}
