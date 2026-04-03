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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardGroupServiceImpl extends ServiceImpl<CardGroupMapper, CardGroupEntity> implements CardGroupService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardGroupEntity getOrSavePubGroup(String cardGroupName) {
        LambdaQueryWrapper<CardGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardGroupEntity::getGroupName,cardGroupName);
        queryWrapper.eq(CardGroupEntity::getBelongUserId,GConst.PUBLIC_USER_ID);
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

    @Override
    public void saveUserCardGroupFromPubCardGroup(Long userId, Long groupId) {
        CardGroupEntity groupEntity = getById(groupId);
        if (null == groupEntity) {
            throw new BusinessException("cardGroup not found");
        }
        Long belongUserId = groupEntity.getBelongUserId();
        if (GConst.PUBLIC_USER_ID != belongUserId) {
            throw new BusinessException("public cardGroup only");
        }
        CardGroupEntity entity = new CardGroupEntity();
        entity.setType(CardGroupTypeEnum.USER.getCode());
        entity.setBelongUserId(userId);
        entity.setGroupName(groupEntity.getGroupName());
        save(entity);
    }
}
