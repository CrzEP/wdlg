package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.NetApiRestApi;
import com.dlg.wdlg.compant.BaiduTranslateRest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NetApiRestApiServiceImpl implements NetApiRestApi {

    @Resource
    BaiduTranslateRest baiduTranslateRest;

    @Override
    public String translate(String q) {
        return baiduTranslateRest.translate(q);
    }

}
