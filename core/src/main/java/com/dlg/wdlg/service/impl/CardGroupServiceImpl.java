package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.comm.CardGroupTypeEnum;
import com.dlg.wdlg.comm.GConst;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.CardGroupMapper;
import com.dlg.wdlg.service.CardGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CardGroupServiceImpl extends ServiceImpl<CardGroupMapper, CardGroupEntity> implements CardGroupService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardGroupEntity getOrSavePubGroup(String cardGroupName) {
        LambdaQueryWrapper<CardGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardGroupEntity::getGroupName, cardGroupName);
        queryWrapper.eq(CardGroupEntity::getBelongUserId, GConst.PUBLIC_USER_ID);
        CardGroupEntity cardGroupEntity = getOne(queryWrapper);
        if (null == cardGroupEntity) {
            // 不存在则保存公共组
            return savePubGroup(cardGroupName);
        }
        return cardGroupEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardGroupEntity savePubGroup(String cardGroupName) {
        CardGroupEntity groupEntity = new CardGroupEntity();
        groupEntity.setGroupName(cardGroupName);
        groupEntity.setType(CardGroupTypeEnum.SOURCE.getCode());
        groupEntity.setBelongUserId(GConst.PUBLIC_USER_ID);
        save(groupEntity);
        return groupEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardGroupEntity addCardGroup(Long userId, String groupName, CardGroupTypeEnum typeEnum) {
        if (StringUtils.isEmpty(groupName) || null == userId) {
            throw new BusinessException("userId and groupName can not be null");
        }
        return saveUserGroup(userId, groupName, typeEnum);
    }

    @Override
    public CardGroupEntity saveUserGroup(Long userId, String groupName, CardGroupTypeEnum typeEnum) {
        Optional<CardGroupEntity> groupEntity = findByUserIdAndGroupNameAndType(userId, groupName, typeEnum);
        if (groupEntity.isPresent()) {
            throw new BusinessException("cardGroup already exist");
        }
        CardGroupEntity cardGroupEntity = new CardGroupEntity();
        cardGroupEntity.setGroupName(groupName);
        cardGroupEntity.setBelongUserId(userId);
        cardGroupEntity.setType(typeEnum.getCode());
        save(cardGroupEntity);
        log.info("add user card group {} success", groupName);
        return cardGroupEntity;
    }

    private Optional<CardGroupEntity> findByUserIdAndGroupNameAndType(Long userId, String groupName, CardGroupTypeEnum typeEnum) {
        LambdaQueryWrapper<CardGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardGroupEntity::getGroupName, groupName);
        queryWrapper.eq(CardGroupEntity::getBelongUserId, userId);
        if (null != typeEnum) {
            queryWrapper.eq(CardGroupEntity::getType, typeEnum.getCode());
        }
        return getOneOpt(queryWrapper);
    }

    @Override
    public void checkPubGroup(Long pubGroupId) {
        checkUserGroup(GConst.PUBLIC_USER_ID, pubGroupId, CardGroupTypeEnum.SOURCE);
    }

    @Override
    public void checkUserLearnGroup(Long userId, Long groupId) {

    }

    @Override
    public void checkUserGroup(Long userId, Long groupId, CardGroupTypeEnum typeEnum) {
        LambdaQueryWrapper<CardGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardGroupEntity::getId, groupId);
        queryWrapper.eq(CardGroupEntity::getBelongUserId, userId);
        queryWrapper.eq(CardGroupEntity::getType, typeEnum.getCode());
        Optional<CardGroupEntity> opt = getOneOpt(queryWrapper);
        if (opt.isPresent()) {
            return;
        }
        throw new BusinessException("not target card group found");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardGroupEntity getUserLearningCardGroup(Long userId) {
        LambdaQueryWrapper<CardGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardGroupEntity::getBelongUserId, userId);
        queryWrapper.eq(CardGroupEntity::getType, CardGroupTypeEnum.USER_LEARNING.getCode());
        Optional<CardGroupEntity> cardGroup = getOneOpt(queryWrapper);
        if (cardGroup.isPresent()) {
            return cardGroup.get();
        }
        return addCardGroup(userId, "default", CardGroupTypeEnum.USER_LEARNING);
    }

    @Override
    public CardGroupEntity getNotNullGroup(Long groupId) {
        Optional<CardGroupEntity> cardGroup = getOptById(groupId);
        if (cardGroup.isPresent()) {
            return cardGroup.get();
        }
        throw new BusinessException("not target card group found");
    }

}
