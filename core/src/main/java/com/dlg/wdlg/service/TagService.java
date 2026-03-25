package com.dlg.wdlg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlg.wdlg.entity.TagEntity;

public interface TagService extends IService<TagEntity> {
    void saveTag();
}
