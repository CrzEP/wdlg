package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.CardNewOptApi;
import com.dlg.wdlg.comm.UserCardDelayEnum;
import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.compant.CacheCollection;
import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.memory.MemoryAdapter;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;
import com.dlg.wdlg.service.*;
import com.dlg.wdlg.util.RandomUtil;
import com.dlg.wdlg.util.UserUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class CardNewOptApiServiceImpl implements CardNewOptApi {

    @Resource
    CardGroupService cardGroupService;
    @Resource
    UserCardInfoService userCardInfoService;
    @Resource
    CardMemoryLogService cardMemoryLogService;
    @Resource
    MemoryAdapter memoryAdapter;
    @Resource
    private WordService wordService;
    @Resource
    private CardService cardService;

    private final Cache<Long, LinkedHashMap<String, UserCardInfoEntity>> userCardCache = CacheCollection.USER_CARD_CACHE;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setCardMemory(Long cardId) {
        UserCardInfoEntity entity = userCardInfoService.getNotNullById(cardId);
        entity.setDelay(UserCardDelayEnum.MEMORY.getCode());
        userCardInfoService.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setRandomCardMemory(long count) {
        List<Long> idList = userCardInfoService.ListDelayUserCard(UserCardDelayEnum.JOIN.getCode());
        long size = idList.size();
        List<Long> addList;
        if (size <= count) {
            // 直接添加整个
            addList = idList;
        } else {
            int num = Math.toIntExact(count);
            addList = randomAdd(num, idList);
        }
        // 等待添加的集合
        List<UserCardInfoEntity> entities = userCardInfoService.listByIds(addList);
        for (UserCardInfoEntity entity : entities) {
            entity.setDelay(UserCardDelayEnum.MEMORY.getCode());
        }
        // 更新状态
        userCardInfoService.updateBatchById(entities);
    }

    /**
     * 随机添加
     *
     * @param num    添加的ID数
     * @param idList 集合
     * @return 随机集合
     */
    private List<Long> randomAdd(int num, List<Long> idList) {
        Set<Long> addList = new HashSet<>(num);
        int size = idList.size();
        for (int i = 0; i < num; i++) {
            int number = RandomUtil.getRandomNumber(0, size);
            while (addList.contains(number)) {
                number = RandomUtil.getRandomNumber(0, size);
            }
            addList.add(idList.get(number));
        }
        return new ArrayList<>(addList);
    }

}
