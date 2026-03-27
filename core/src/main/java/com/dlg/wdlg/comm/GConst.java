package com.dlg.wdlg.comm;

public interface GConst {

    String VERSION = "1.0.0";

    /**
     * 正常
     */
    int ENABLE = 1;

    /**
     * 停用
     */
    int DISABLE = 2;

    /**
     * 默认公开组
     */
    long PUBLIC_GROUP_ID = 0;


    /**
     * 默认用户ID
     */
    long PUBLIC_USER_ID = PUBLIC_GROUP_ID;

    /**
     * 添加字符记录日志
     */
    String ADD_STR_RECORD_LOG = "cardAddLog";

}
