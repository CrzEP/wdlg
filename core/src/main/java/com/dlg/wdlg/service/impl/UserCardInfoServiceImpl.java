package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.UserCardDelayEnum;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.UserCardInfoMapper;
import com.dlg.wdlg.memory.fsrs.FSRSUtil;
import com.dlg.wdlg.service.CardGroupService;
import com.dlg.wdlg.service.CardService;
import com.dlg.wdlg.service.UserCardInfoService;
import com.dlg.wdlg.util.UserUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserCardInfoServiceImpl extends ServiceImpl<UserCardInfoMapper, UserCardInfoEntity> implements UserCardInfoService {

    @Resource
    CardGroupService cardGroupService;
    @Resource
    CardService cardService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserCardGroup(Long userId, Long sourGroupId) {
        // 检查属于此用户的卡组
        cardGroupService.checkPubGroup(sourGroupId);
        CardGroupEntity pubGroup = cardGroupService.getNotNullGroup(sourGroupId);
        List<Long> cardIs = cardService.listIdsByGroupId(sourGroupId);
        for (Long cardId : cardIs) {
            addUserCardInfo(userId, cardId);
        }
        log.info("添加公共卡组： {} 完毕，共 {} 个",
                pubGroup.getGroupName(), cardIs.size());
    }

    @Override
    public UserCardInfoEntity getNotNullByCardId(Long cardId) {
        Optional<UserCardInfoEntity> entity = getOptById(cardId);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new BusinessException("card not found");
    }

    /**
     * 添加用户卡信息
     *
     * @param userId userId
     * @param cardId cardId
     */
    @Override
    public UserCardInfoEntity addUserCardInfo(Long userId, Long cardId) {
        CardGroupEntity userLearningCardGroup = cardGroupService.getUserLearningCardGroup(userId);
        Long userLearningCardGroupId = userLearningCardGroup.getId();
        // 检查卡片
        getNotNullByCardId(cardId);
        // 创建记忆卡
        UserCardInfoEntity entity = FSRSUtil.newCard();
        // 设定初始参数
        entity.setCardId(cardId);
        entity.setBelongUserId(userId);
        entity.setBelongGroupId(userLearningCardGroupId);
        entity.setDelay(UserCardDelayEnum.JOIN.getCode());
        entity.setCardCostMils(0L);
        entity.setCardCostDay(0L);
        entity.setMemoryCount(0);
        save(entity);
        return entity;
    }

    @Override
    public UserCardInfoEntity getNotNullById(Long id) {
        Optional<UserCardInfoEntity> entity = getOptById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new BusinessException("userCardInfo not found");
    }

    @Override
    public List<Long> ListDelayUserCard(Integer type) {
        return this.lambdaQuery()
                .eq(UserCardInfoEntity::getDelay, type)
                .select(UserCardInfoEntity::getId)
                .list()
                .stream()
                .map(UserCardInfoEntity::getId)
                .toList();
    }

    @Override
    public List<UserCardInfoEntity> listTodayMemoryCards(Long groupId) {
        Long userId = UserUtil.getUserIdNotNull();
        LambdaQueryChainWrapper<UserCardInfoEntity> queryWrapper = this.lambdaQuery()
                .select(UserCardInfoEntity::getId)
                .eq(UserCardInfoEntity::getDelay, UserCardDelayEnum.MEMORY.getCode())
                .eq(UserCardInfoEntity::getBelongGroupId, groupId)
                .eq(UserCardInfoEntity::getBelongUserId, userId);
        LocalDate now = LocalDate.now();
        LocalDateTime startTime = now.atStartOfDay();
        LocalDateTime endTime = now.atStartOfDay().plusDays(1).plusSeconds(-1);
        queryWrapper.between(UserCardInfoEntity::getNextMemoryTime,startTime,endTime);
        return this.list(queryWrapper);
    }

}
