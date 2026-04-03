package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.StrRecordEntity;

public interface StrRecordService extends IService<StrRecordEntity> {

    /**
     *
     * @param entity
     */
    void saveIfNotExists(StrRecordEntity entity);

    /**
     * 根据tag获取记录/可能很多条
     *
     * @param pageDTO pageDTO
     * @param strTag  strTag
     * @return 记录
     */
    PageDTO<StrRecordEntity> pageByStrTag(PageDTO<StrRecordEntity> pageDTO, String strTag);

}
