package com.dlg.wdlg.api;

/**
 * 网络其他API 调用
 */
public interface NetApiRestApi {

    /**
     * 翻译
     * @param q 中文
     * @return 英文
     */
    String translate(String q);

}
