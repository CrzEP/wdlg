package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.StrRecordEntity;
import com.dlg.wdlg.mapper.StrRecordMapper;
import com.dlg.wdlg.service.StrRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StrRecordServiceImpl extends ServiceImpl<StrRecordMapper, StrRecordEntity> implements StrRecordService {

    @Override
    public void saveIfNotExists(StrRecordEntity entity) {
        LambdaQueryWrapper<StrRecordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrRecordEntity::getKeyword, entity.getKeyword());
        StrRecordEntity record = this.getOne(queryWrapper);
        if (null == record) {
            save(entity);
            log.info("保存 StrRecordEntity :{} ", entity);
        }
    }
}
