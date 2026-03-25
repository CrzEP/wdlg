package com.dlg.wdlg.api;

import com.dlg.wdlg.pojos.TodayCardInfoPojo;

public interface AccountApi {

    /**
     * 活哦去今日卡片信息
     * @return 卡片信息
     */
    TodayCardInfoPojo getTodayCardInfo();

}
