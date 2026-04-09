package com.dlg.wdlg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.mapper.CardMemoryLogMapper;
import com.dlg.wdlg.service.CardMemoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardMemoryServiceImpl extends ServiceImpl<CardMemoryLogMapper, CardMemoryLogEntity> implements CardMemoryLogService {

}
