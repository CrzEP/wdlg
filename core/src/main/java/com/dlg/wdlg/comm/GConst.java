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
     * 原始卡/公共卡组
     */
    int SOURCE_GROUP = 0;

    /**
     * 用户组卡
     */
    int USER_GROUP = 1;

    /**
     * 添加字符记录日志
     */
    String ADD_STR_RECORD_LOG = "cardAddLog";

    /**
     * 百词
     */
    String BAICI_10K = "baici-10K";

    /**
     * 雅思
     */
    String ITELS_2012_6K = "ITELS-2012-6K";

    /**
     * token 字段
     */
    String TOKEN = "token";

}
