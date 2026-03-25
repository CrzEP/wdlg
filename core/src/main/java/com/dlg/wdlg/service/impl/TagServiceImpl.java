package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.TagEntity;
import com.dlg.wdlg.mapper.TagMapper;
import com.dlg.wdlg.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    @Override
    public void saveTag() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName("tag_"+System.currentTimeMillis());
        tagEntity.setState(1);
        tagEntity.setCreateTime(new Date());
        save(tagEntity);
    }
}
