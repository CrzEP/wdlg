package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.LoadWordService;
import com.dlg.wdlg.compant.JsonWordFileLoadComp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LoadWordServiceImpl implements LoadWordService {

    @Resource
    private JsonWordFileLoadComp jsonWordFileLoadComp;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadFromJsonWordFile(String jsonFilePath) {
        jsonWordFileLoadComp.loadJsonWordFile(jsonFilePath);
    }
}
