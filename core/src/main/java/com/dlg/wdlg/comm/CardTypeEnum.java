package com.dlg.wdlg.comm;

/**
 * 卡类型
 */
public enum CardTypeEnum implements IterEnum<Integer>{

    /**
     * 单词卡
     */
    WORD(1);

    private final int code;

    CardTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
