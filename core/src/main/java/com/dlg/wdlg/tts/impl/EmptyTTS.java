package com.dlg.wdlg.tts.impl;

import com.dlg.wdlg.tts.LexicoTTS;
import lombok.extern.slf4j.Slf4j;

/**
 * 空tts
 */
@Slf4j
public class EmptyTTS implements LexicoTTS {

    @Override
    public void play(String word) {
        log.info("EmptyTTS play word: {}",word);
    }

    @Override
    public void saveVoiceFile(String word, String filePath) {
        log.info("EmptyTTS saveVoiceFile word: {}  filePath: {}",word,filePath);
    }
}
