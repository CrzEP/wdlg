package com.dlg.wdlg.compant;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dlg.wdlg.comm.CardTypeEnum;
import com.dlg.wdlg.comm.GConst;
import com.dlg.wdlg.entity.*;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.mapper.WordParaphraseMapper;
import com.dlg.wdlg.mapper.WordSentenceMapper;
import com.dlg.wdlg.service.*;
import com.dlg.wdlg.util.JsonFileUtil;
import com.dlg.wdlg.util.JsonUtils;
import com.google.common.io.LineReader;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
public class JsonWordFileLoadComp {

    @Resource
    private WordService wordService;
    @Resource
    private CardService cardService;
    @Resource
    private CardGroupService cardGroupService;
    @Resource
    private CardMnemonicService mnemonicService;
    @Resource
    private WordParaphraseMapper wordParaphraseMapper;
    @Resource
    private WordSentenceMapper wordSentenceMapper;
    @Resource
    private CardRelativeService relativeService;
    @Resource
    private WordQueryService wordQueryService;

    @Transactional(rollbackFor = Exception.class)
    public void loadJsonWordFile(String jsonFilePath) {
        File file = new File(jsonFilePath);
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        List<WordInfo> wordInfoList = loadJsonWordFile(file);
        CardGroupEntity groupEntity = cardGroupService.getOrSavePubGroup(fileName);
        saveWordList(wordInfoList, groupEntity);
    }

    /**
     * 保存单词集合
     *
     * @param wordInfoList 原词对象集合
     * @param groupEntity  所属卡组
     */
    private void saveWordList(List<WordInfo> wordInfoList, CardGroupEntity groupEntity) {
        int count = 0;
        for (WordInfo wordInfo : wordInfoList) {
            boolean flag = saveWordInfo(wordInfo, groupEntity.getId());
            if (flag) {
                count++;
            }
        }
        for (WordInfo wordInfo : wordInfoList) {
            relWords(wordInfo);
        }
        log.info("加载单词卡 ： {} 个", wordInfoList.size());
        log.info("已保存单词卡 ： {} 个", count);
    }

    /**
     * 关联单词
     *
     * @param wordInfo wordInfo
     */
    private void relWords(WordInfo wordInfo) {
        WordEntity sourWordEntity = wordService.findByWords(wordInfo.getWord());
        if (sourWordEntity == null) {
            return;
        }
        List<String> wordList = wordInfo.getRelWordList();
        if (CollectionUtils.isEmpty(wordList)) {
            return;
        }
        for (String word : wordList) {
            WordEntity relWord = wordService.findByWords(word);
            if (relWord != null) {
                relativeService.saveWordRel(sourWordEntity.getId(), relWord.getId());
            }
        }
    }

    /**
     * 保存单词卡信息
     *
     * @param wordInfo 单词信息
     * @param groupId  所属组
     */
    private boolean saveWordInfo(WordInfo wordInfo, Long groupId) {
        String word = wordInfo.getWord();
        if (StringUtils.isEmpty(word)) {
            throw new BusinessException("word is empty");
        }
        CardEntity cardEntity = wordQueryService.getCardByWordAndGroupId(word, groupId);
        if (null != cardEntity) {
            // 已存在则不添加
            return false;
        }
        // 保存单词
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWord(wordInfo.getWord());
        wordEntity = wordService.savePubWord(wordEntity);
        // 保存卡片
        CardEntity card = new CardEntity();
        card.setType(CardTypeEnum.WORD.getCode());
        card.setBelongGroupId(groupId);
        card.setBelongUserId(GConst.PUBLIC_USER_ID);
        card.setContentId(wordEntity.getId());
        cardService.savePubWordCard(card);
        // 保存助记
        String helpRem = wordInfo.getHelpRem();
        if (!StringUtils.isEmpty(helpRem)) {
            mnemonicService.saveHelpRem(card.getId(), helpRem);
        }
        // 保存释义
        List<ParaphraseInfo> wordCnList = wordInfo.getParaphraseInfoList();
        if (CollectionUtils.isNotEmpty(wordCnList)) {
            List<WordParaphraseEntity> paraphraseList = new ArrayList<>(wordCnList.size());
            for (ParaphraseInfo paraphraseInfo : wordCnList) {
                WordParaphraseEntity entity = new WordParaphraseEntity();
                entity.setPos(paraphraseInfo.getPos());
                entity.setParaphrase(paraphraseInfo.getParaphrase());
                entity.setWordId(wordEntity.getId());
                paraphraseList.add(entity);
            }
            wordParaphraseMapper.insert(paraphraseList);
        }
        // 保存句子
        List<SentenceInfo> sentenList = wordInfo.getSentenceInfoList();
        if (CollectionUtils.isNotEmpty(sentenList)) {
            List<WordSentenceEntity> sentenceEntityList = new ArrayList<>(sentenList.size());
            int i = 1;
            for (SentenceInfo sentenceInfo : sentenList) {
                WordSentenceEntity entity = new WordSentenceEntity();
                entity.setWordId(wordEntity.getId());
                entity.setSentence(sentenceInfo.getSentence());
                entity.setSentenceCn(sentenceInfo.getSentenceCn());
                entity.setSort(i);
                i++;
                sentenceEntityList.add(entity);
            }
            wordSentenceMapper.insert(sentenceEntityList);
        }
        return true;
    }

    /**
     * 从文件加载
     *
     * @param file 单词json文件
     * @return 映射
     */
    public static List<WordInfo> loadJsonWordFile(File file) {
        LineReader lineReader = JsonFileUtil.loadJsonAsLineReader(file);
        List<WordInfo> wordList = new ArrayList<>();
        String nextLine = JsonFileUtil.nextLine(lineReader);
        while (StringUtils.isNotBlank(nextLine)) {
            // 解析
            WordInfo wordInfo = analyseJsonWord(nextLine);
            // 放入容器
            wordList.add(wordInfo);
            // 读取
            nextLine = JsonFileUtil.nextLine(lineReader);
        }
        return wordList;
    }

    /**
     * 解析单行json单词
     *
     * @param nextLine 行内容
     * @return 单个词相关信息
     */
    private static WordInfo analyseJsonWord(String nextLine) {
        WordInfo wordInfo = new WordInfo();
        Map<String, Object> map = JsonUtils.readAsMap(nextLine);
        int indexSort = (int) map.get("wordRank");
        String headWord = (String) map.get("headWord");
        wordInfo.setWord(headWord);
        Map<String, Object> content = (Map<String, Object>) map.get("content");
        Map<String, Object> word = (Map<String, Object>) content.get("word");
        Map<String, Object> content2 = (Map<String, Object>) word.get("content");
        // 例句
        Map<String, Object> sentence = (Map<String, Object>) content2.get("sentence");
        if (null != sentence) {
            List<Map<String, String>> sentenceList = (List<Map<String, String>>) sentence.get("sentences");
            List<SentenceInfo> sentenceInfoList = new ArrayList<>(sentenceList.size());
            for (Map<String, String> sentenceMap : sentenceList) {
                String sContent = sentenceMap.get("sContent");
                String sCn = sentenceMap.get("sCn");
                sentenceInfoList.add(new SentenceInfo(sContent, sCn));
            }
            wordInfo.setSentenceInfoList(sentenceInfoList);
        } else {
            wordInfo.setSentenceInfoList(new ArrayList<>());
        }
        // 近义词/反义词
        Map<String, Object> syno = (Map<String, Object>) content2.get("syno");
        //短语
        Map<String, Object> phrase = (Map<String, Object>) content2.get("phrase");
        if (null != phrase) {
            List<Map<String, Object>> phrases = (List<Map<String, Object>>) phrase.get("phrases");
            List<PhrasesInfo> phrasesInfoList = new ArrayList<>(phrases.size());
            for (Map<String, Object> phraseMap : phrases) {
                String pContent = phraseMap.get("pContent").toString();
                String pCn = phraseMap.get("pCn").toString();
                phrasesInfoList.add(new PhrasesInfo(pContent, pCn));
            }
            wordInfo.setPhrasesInfoList(phrasesInfoList);
        }
        Map<String, Object> remMethod = (Map<String, Object>) content2.get("remMethod");
        if (null != remMethod) {
            // 助记
            String helpRem = remMethod.get("val").toString();
            wordInfo.setHelpRem(helpRem);
        }
        // 关联单词
        Map<String, Object> relWord = (Map<String, Object>) content2.get("relWord");
        if (null != relWord) {
            List<String> stringList = readRelWord(relWord);
            wordInfo.setRelWordList(stringList);
        }
        // 翻译
        List<Map<String, String>> transList = (List<Map<String, String>>) content2.get("trans");
        List<ParaphraseInfo> paraphraseInfoList = new ArrayList<>(transList.size());
        for (Map<String, String> transMap : transList) {
            String tranCn = transMap.get("tranCn");
            String pos = transMap.get("pos");
            paraphraseInfoList.add(new ParaphraseInfo(tranCn, pos));
        }
        wordInfo.setParaphraseInfoList(paraphraseInfoList);
        return wordInfo;
    }

    /**
     * 读取关联词
     *
     * @param relWord 关联词map
     * @return 关联词
     */
    private static List<String> readRelWord(Map<String, Object> relWord) {
        List<String> relWordList = new ArrayList<>();
        List<Map<String, Object>> rels = (List<Map<String, Object>>) relWord.get("rels");
        if (CollectionUtils.isEmpty(rels)) {
            return relWordList;
        }
        for (Map<String, Object> relMap : rels) {
            List<Map<String, Object>> wordsMap = (List<Map<String, Object>>) relMap.get("words");
            if (null == wordsMap) {
                continue;
            }
            for (Map<String, Object> wordMap : wordsMap) {
                String words = wordMap.get("hwd").toString();
                if (StringUtils.isNotBlank(words)) {
                    relWordList.add(words);
                }
            }
        }
        return relWordList;
    }

    @Data
    static class WordInfo {

        /**
         * 单词
         */
        private String word;

        /**
         * 助记
         */
        private String helpRem;

        /**
         * 关联单词
         */
        private List<String> relWordList;

        /**
         * 释义
         */
        private List<ParaphraseInfo> paraphraseInfoList;

        /**
         * 句子
         */
        private List<SentenceInfo> sentenceInfoList;

        /**
         * 短语
         */
        private List<PhrasesInfo> phrasesInfoList;

    }

    @AllArgsConstructor
    @Data
    static class ParaphraseInfo {
        private String paraphrase;

        private String pos;
    }

    @AllArgsConstructor
    @Data
    static class SentenceInfo {
        private String sentence;
        private String sentenceCn;
    }

    @AllArgsConstructor
    @Data
    static class PhrasesInfo {
        private String phrases;
        private String phrasesCn;
    }

}
