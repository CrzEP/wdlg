package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.AccountApi;
import com.dlg.wdlg.pojos.TodayCardInfoPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountApiServiceImpl implements AccountApi {

    @Override
    public TodayCardInfoPojo getTodayCardInfo() {
        return null;
    }

}
