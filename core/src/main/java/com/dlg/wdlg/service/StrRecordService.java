package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.StrRecordEntity;

public interface StrRecordService extends IService<StrRecordEntity> {

    /**
     *
     * @param entity
     */
    void saveIfNotExists(StrRecordEntity entity);
}
