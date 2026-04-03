package com.dlg.wdlg.comm;

/**
 * 助记方式：
 * 1句子：特殊句，非例句
 * 2记忆方式：一段话
 * 3发音文件：帮助记忆的读音,一般只想播放文件地址
 * 4图片：形象记忆
 */
public enum MnemonicTypeEnum implements IterEnum<Integer> {

    /**
     * 句子
     */
    SENTENCE(1),

    /**
     * 记忆方式
     */
    MEANING(2),

    /**
     * 发音文件:MAC 发音文件
     */
    AIFF(3),

    /**
     * 发音文件:MAC 发音文件
     */
    IMG(4);

    private final int code;

    MnemonicTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
