package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.InfoApi;
import com.dlg.wdlg.comm.GConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InfoApiServiceImpl implements InfoApi {

    @Override
    public String version() {
        return GConst.VERSION;
    }

}
