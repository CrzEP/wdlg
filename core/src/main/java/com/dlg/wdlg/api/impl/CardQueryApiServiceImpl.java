package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.CardQueryApi;
import com.dlg.wdlg.entity.CardGroupEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.service.UserCardInfoService;
import com.dlg.wdlg.util.UserUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CardQueryApiServiceImpl implements CardQueryApi {

    @Resource
    UserCardInfoService userCardInfoService;

    @Override
    public List<UserCardInfoEntity> findTodayMemoryCardInfoList(Long max) {
        CardGroupEntity learnGroup = UserUtil.getUserLearnGroupEntityWithNonull();
        List<UserCardInfoEntity> infoEntities = userCardInfoService.listTodayMemoryCards(learnGroup.getId());
        getMaxList(infoEntities, max);
        // 组装参数 fixme
        return infoEntities;
    }

    /**
     * 最多获取max条
     *
     * @param infoEntities 已有集合
     * @param max          最多条数
     */
    private void getMaxList(List<UserCardInfoEntity> infoEntities, Long max) {
        int size = infoEntities.size();
        if (size <= max || max < 0) {
            return;
        }
        // 从早到晚排序
        infoEntities.sort(Comparator.comparing(UserCardInfoEntity::getNextMemoryTime));
        // 取最前面的
        infoEntities.subList(0, Math.toIntExact(max));
    }

}
