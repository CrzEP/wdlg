package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.CardOptApi;
import com.dlg.wdlg.comm.UserCardDelayEnum;
import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.memory.MemoryAdapter;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;
import com.dlg.wdlg.service.CardMemoryLogService;
import com.dlg.wdlg.service.UserCardInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CardOptApiServiceImpl  implements CardOptApi {

    @Resource
    UserCardInfoService userCardInfoService;
    @Resource
    CardMemoryLogService cardMemoryLogService;
    @Resource
    MemoryAdapter memoryAdapter;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delayCard(Long cardId, UserCardDelayEnum delay) {
        UserCardInfoEntity cardInfo = userCardInfoService.getNotNullByCardId(cardId);
        cardInfo.setDelay(delay.getCode());
        userCardInfoService.updateById(cardInfo);
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
}
