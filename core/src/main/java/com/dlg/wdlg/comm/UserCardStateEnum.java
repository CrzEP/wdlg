package com.dlg.wdlg.comm;

/**
 * 用户卡状态枚举
 */
public enum UserCardStateEnum implements IterEnum<Integer> {

    /**
     * 熟悉
     */
    FAMILIAR(1),
    /**
     * 模糊
     */
    BLUR(2),
    /**
     * 陌生
     */
    UNFAMILIAR(3);

    private final int code;

    UserCardStateEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
