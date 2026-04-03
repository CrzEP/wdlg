package com.dlg.wdlg.comm;

public enum CardGroupTypeEnum implements IterEnum<Integer> {

    /**
     * 原始卡/公共卡
     */
    SOURCE(GConst.SOURCE_GROUP),

    /**
     * 用户组卡
     */
    USER(GConst.USER_GROUP),
    USER_LEARNING(GConst.USER_GROUP);

    private final int code;

    CardGroupTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
