package com.dlg.wdlg.api;


import java.util.List;

public interface AddCardApi {

    /**
     * 加载Csv Card
     *
     * @param filePath 文件路径
     * @param cardGroupName 卡片组名
     */
    void loadCsvCard(String filePath,String cardGroupName);


    void loadStrCard(List<List<String>> wordMap, String cardGroupName);

}
