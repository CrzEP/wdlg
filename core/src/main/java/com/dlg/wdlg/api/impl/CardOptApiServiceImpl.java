package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.CardOptApi;
import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.memory.MemoryAdapter;
import com.dlg.wdlg.pojos.CardInfoPojo;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;
import com.dlg.wdlg.service.CardGroupService;
import com.dlg.wdlg.service.CardMemoryLogService;
import com.dlg.wdlg.service.UserCardInfoService;
import com.dlg.wdlg.util.UserUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Optional;

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

    @Override
    public CardInfoPojo getNextCard() {
        // 计算

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void optCard(Long cardId, UserCardStateEnum statge, Long optTime) {
        Long userId = UserUtil.getUserIdNotNull();
        UserCardInfoEntity userCardInfo = userCardInfoService.findByUserIdAndCardId(userId, cardId);
        // 复习
        if (optTime == null) {
            optTime = System.currentTimeMillis();
        }
        CardUserAndLogInfo userAndLogInfo = memoryAdapter.review(userCardInfo, statge, optTime);
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
    public void addLoginUserPubCardGroup(Long groupId) {
        // 检查组
        Optional<CardGroupEntity> groupEntity = cardGroupService.getOptById(groupId);
        if (groupEntity.isEmpty()) {
            throw new BusinessException("cardGroup not found");
        }
        Long userId = UserUtil.getUserIdNotNull();
        // 添加用户卡组
        cardGroupService.saveUserCardGroupFromPubCardGroup(userId, groupId);
        // 添加用户卡组单词信息
        userCardInfoService.addUserCardGroup(userId, groupId);
        log.info("已添加用户公共卡组：{}", groupEntity.get().getGroupName());
    }

}
