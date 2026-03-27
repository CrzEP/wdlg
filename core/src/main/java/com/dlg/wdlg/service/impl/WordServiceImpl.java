package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.WordEntity;
import com.dlg.wdlg.mapper.WordMapper;
import com.dlg.wdlg.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, WordEntity> implements WordService {

}
