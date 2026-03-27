package com.dlg.wdlg.tts.impl;

import com.dlg.wdlg.config.WdlgValue;
import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.tts.LexicoTTS;
import com.dlg.wdlg.util.CmdUtil;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Data
@Slf4j
@Component
public class MacTTS implements LexicoTTS {

    /**
     * 发音速度:175默认
     */
    private int SPEED = 175;

    /**
     * 声音名：samantha 默认
     */
    private String Voice_TYPE = "Samantha";

    @Resource
    WdlgValue wordValue;


    @Override
    public void play(String word) {
        File wordFile = findOrSaveVoiceFile(word);
        macPlayFile(wordFile.getAbsolutePath());
    }

    /**
     * 播放
     *
     * @param wordFile 单词文件
     */
    private void macPlayFile(String wordFile) {
        try {
            String cmd = "afplay " + wordFile;
            CmdUtil.exec(cmd);
        } catch (IOException e) {
            log.error("MacTTS macPlay error: ", e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 组合命令
     *
     * @param word 单词
     * @return 命令
     */
    private String combine(String word) {
        // 形如 say -r 175 -v Samantha "hello"
        return "say " +
                "-r " + SPEED + " " +
                "-v " + Voice_TYPE + " " +
                "\"" + word + "\" ";
    }

    @Override
    public void saveVoiceFile(String word, String filePath) {
        String cmd = combine(word) + "-o " + filePath;
        try {
            CmdUtil.exec(cmd);
        } catch (IOException e) {
            log.error("MacTTS saveVoiceFile error: ", e);
            throw new BusinessException(e.getMessage());
        }
    }

    public File findOrSaveVoiceFile(String word) {
        String localWordDir = wordValue.getLocalWordDir();
        String filePath = localWordDir + word;
        File file = new File(filePath);
        if (!file.exists()) {
            saveVoiceFile(word, filePath);
            return findOrSaveVoiceFile(word);
        }
        return file;
    }

}
