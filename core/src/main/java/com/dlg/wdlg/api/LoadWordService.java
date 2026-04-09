package com.dlg.wdlg.api;

/**
 * 导入/载入单词
 */
public interface LoadWordService {

    /**
     * 从文件中加载：仅支持支持的json格式
     *
     * @param jsonFilePath jsonFilePath
     */
    void loadFromJsonWordFile(String jsonFilePath);

}
