package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.CardRelativeEntity;

public interface CardRelativeService extends IService<CardRelativeEntity> {

    /**
     * 保存关联
     * @param sour sour
     * @param dest dest
     */
    void saveWordRel(Long sour, Long dest);

}
