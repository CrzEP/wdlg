package com.dlg.wdlg.tts;

import com.dlg.wdlg.tts.impl.EmptyTTS;
import com.dlg.wdlg.tts.impl.MacTTS;
import lombok.Getter;

/**
 * TTS 工厂
 */
@Getter
public class TTSFacotry {

    private static volatile LexicoTTS lexicoTTS;

    /**
     * 获取一个TTS
     * @return tts
     */
    public LexicoTTS getLexicoTTS() {
        if (lexicoTTS == null) {
            synchronized (TTSFacotry.class) {
                if (lexicoTTS == null) {
                    lexicoTTS = instanceTTS();
                }
            }
        }
        return lexicoTTS;
    }

    /**
     * 实例化一个TTS
     *
     * @return tts
     */
    private static LexicoTTS instanceTTS() {
        switch (LexicoTTS.TTS_TYPE) {
            case "mac":
                lexicoTTS = new MacTTS();
                break;
            default:
                lexicoTTS = new EmptyTTS();
        }
        return lexicoTTS;
    }

}
