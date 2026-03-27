package com.dlg.wdlg.compant;

import com.dlg.wdlg.entity.StrRecordEntity;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.service.StrRecordService;
import com.dlg.wdlg.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 从这里加载：https://github.com/lyc8503/baicizhan-word-meaning-API
 * 10927个单词
 */
@Component
@Slf4j
public class LoadFromBaiCi {

    private final String baici = "baici";

    @Resource
    StrRecordService strRecordService;


    /**
     * 开始处理
     */
    public void startReq() {
        File dir = new File("/Users/lingui/project/linguiProject/JavaProject/wdlg/wdlg/localData/doc/words");
        File[] files = dir.listFiles();
        List<String> list = new ArrayList<>(files.length);
        for (File file : files) {
            String json = loadJsonFile(file);
            list.add(json);
        }
        List<WordDTO> wordList = new ArrayList<>(list.size());
        log.info("开始加载常用单词 {} ，数量 {}", "parseZpk", list.size());
        for (String word : list) {
            WordDTO wordDTO = JsonUtils.fromJson(word, WordDTO.class);
            if (null != wordDTO) {
                wordDTO.setSourJson(word);
                wordList.add(wordDTO);
            }
        }
        saveStrRecord(wordList);
        log.info("储存单词 {} ，数量 {}", "parseZpk", wordList.size());
    }

    private String loadJsonFile(File file) {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BusinessException("loadJsonFile fail : " + e);
        }
    }

    private void saveStrRecord(List<WordDTO> list) {
        List<StrRecordEntity> strRecordEntityList = new ArrayList<>(list.size());
        long intTag = System.currentTimeMillis();
        long index = 0;
        Set<String> wordSet = new HashSet<>(list.size());
        for (WordDTO wordDTO : list) {
            StrRecordEntity entity = new StrRecordEntity();
            entity.setKeyword(wordDTO.getWord());
            entity.setContent(wordDTO.getSourJson());
            entity.setStrTag(baici);
            entity.setIntTag(intTag);
            entity.setSort(++index);
            if (wordSet.contains(entity.getKeyword())) {
                continue;
            }
            strRecordEntityList.add(entity);
            wordSet.add(entity.getKeyword());
        }
        strRecordService.saveBatch(strRecordEntityList);
    }


    @Data
    public static class BCList {
        String total;

        List<String> list;
    }

    @Data
    public static class WordDTO {

        /**
         * 单词
         */
        private String word;
        /**
         * 发音
         */
        private String accent;
        /**
         * 中文释义
         */
        private String meanCn;
        /**
         * 英文解释(可能为空字符串, 但绝大多数单词有英文解释.)
         */
        private String meanEn;
        /**
         * 英文例句(可能为空字符串, 但大部分单词有例句.)
         */
        private String sentence;
        /**
         * 英文例句翻译
         */
        private String sentenceTrans;
        /**
         * 相关的短语(可能为空字符串)
         */
        private String sentencePhrase;
        /**
         * 单词词源(可能为空字符串)
         */
        private String wordEtyma;

        private ClozeData clozeData;

        /**
         * 原始json
         */
        @JsonIgnore
        private String sourJson;

        @Data
        public static class ClozeData {
            private String syllable;
            private String cloze;
            private List<String> options;
            private List<List<String>> tips;
        }
    }

}
