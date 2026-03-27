package com.dlg.wdlg.compant;

import com.dlg.wdlg.api.AddCardApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadFromCsv {

    @Resource
    AddCardApi addCardApi;

    /**
     * 开始处理
     */
    public void startReq() {
        String filePath = "/Users/lingui/project/linguiProject/JavaProject/wdlg/wdlg/localData/doc/2012-ITELS.csv";
        addCardApi.loadCsvCard(filePath,"2012-ITELS");

    }

}
