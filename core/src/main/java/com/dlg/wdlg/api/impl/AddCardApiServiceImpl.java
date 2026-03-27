package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.AddCardApi;
import com.dlg.wdlg.comm.CardTypeEnum;
import com.dlg.wdlg.entity.StrRecordEntity;
import com.dlg.wdlg.service.StrRecordService;
import com.dlg.wdlg.util.CsvUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AddCardApiServiceImpl implements AddCardApi {

    @Resource
    StrRecordService strRecordService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadCsvCard(String filePath, String cardGroupName) {
        List<List<String>> contents = CsvUtil.loadCsvFile(filePath, new String[]{"w", "p"});
        addWordArrays(contents, cardGroupName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadStrCard(List<List<String>> wordMap, String cardGroupName) {
        addWordArrays(wordMap, cardGroupName);
    }

    /**
     * 从二元数组加载内容
     * @param contents 单词内容
     * @param cardGroupName 分组名
     */
    private void addWordArrays(List<List<String>> contents, String cardGroupName) {
        if (contents.isEmpty()) {
            return;
        }
        log.info("准备添加卡片，数量：{} 个", contents.size());
        List<StrRecordEntity> recordList = combineStrRecordEntities(contents, cardGroupName);
        strRecordService.saveBatch(recordList);
        log.info("卡片，数量：{} 个，已保存记录", contents.size());
    }

    /**
     * 组合
     * @param contents 内容
     * @param cardGroupName 组名
     * @return 集合
     */
    private static List<StrRecordEntity> combineStrRecordEntities(List<List<String>> contents, String cardGroupName) {
        long index = 0;
        long tag = CardTypeEnum.WORD.getCode();
        List<StrRecordEntity> recordList = new ArrayList<>();
        for (List<String> row : contents) {
            StrRecordEntity recordEntity = new StrRecordEntity();
            recordEntity.setKeyword(row.get(0));
            recordEntity.setContent(row.get(1));
            recordEntity.setIntTag(tag);
            // 分组
            recordEntity.setStrTag(cardGroupName);
            recordEntity.setSort(++index);
            recordList.add(recordEntity);
        }
        return recordList;
    }


}
