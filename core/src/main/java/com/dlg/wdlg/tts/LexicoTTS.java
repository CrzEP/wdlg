package com.dlg.wdlg.tts;

/**
 * tts 接口
 */
public interface LexicoTTS {

    String TTS_TYPE = "mac";

    /**
     * 直接播放
     *
     * @param word 单词
     */
    void play(String word);

    /**
     * 保存声音文件
     *
     * @param word     单词
     * @param filePath 储存文件地址：/Users/lingui/tmp/tts/hello.aiff
     */
    void saveVoiceFile(String word, String filePath);

}
