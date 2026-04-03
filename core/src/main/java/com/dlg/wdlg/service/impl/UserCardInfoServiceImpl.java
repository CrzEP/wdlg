package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.UserCardDelayEnum;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.UserCardInfoMapper;
import com.dlg.wdlg.memory.fsrs.FSRSUtil;
import com.dlg.wdlg.service.CardGroupService;
import com.dlg.wdlg.service.CardService;
import com.dlg.wdlg.service.UserCardInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void addUserCardGroup(Long userId, Long groupId) {
        List<Long> cardIs = cardService.listIdsByGroupId(groupId);
        List<UserCardInfoEntity> userCardList = new ArrayList<>(cardIs.size());
        for (Long cardId : cardIs) {
            UserCardInfoEntity entity = addUserCardInfo(userId, cardId, groupId);
            userCardList.add(entity);
        }
        saveBatch(userCardList);
    }

    @Override
    public UserCardInfoEntity findByUserIdAndCardId(Long userId, Long cardId) {
        LambdaQueryWrapper<UserCardInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserCardInfoEntity::getBelongUserId,userId);
        queryWrapper.eq(UserCardInfoEntity::getCardId,cardId);
        Optional<UserCardInfoEntity> oneOpt = this.getOneOpt(queryWrapper);
        if(oneOpt.isPresent()){
            return oneOpt.get();
        }
        throw new BusinessException("userCard not found");
    }

    /**
     * 添加用户卡信息
     *
     * @param userId  userId
     * @param cardId  cardId
     * @param groupId groupId
     */
    private UserCardInfoEntity addUserCardInfo(Long userId, Long cardId, Long groupId) {
        // 创建记忆卡
        UserCardInfoEntity entity = FSRSUtil.newCard();
        // 设定初始参数
        entity.setCardId(cardId);
        entity.setBelongUserId(userId);
        entity.setBelongGroupId(groupId);
        entity.setDelay(UserCardDelayEnum.JOIN.getCode());
        entity.setCardCostMils(0L);
        entity.setCardCostDay(0L);
        entity.setMemoryCount(0);
        return entity;
    }

}
