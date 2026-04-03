package com.dlg.wdlg.comm;

/**
 * 用户卡延迟类型
 * 1加入但未开始记忆 2正常 3延迟记忆 4避免记忆
 *
 */
public enum UserCardDelayEnum implements IterEnum<Integer> {

    /**
     * 1加入但未开始记忆
     */
    JOIN(1),

    /**
     * 正常
     */
    MEMORY(2),

    /**
     * 延迟记忆
     */
    DELAY(3),

    /**
     * 避免记忆
     */
    AVOID(4);

    private final int code;

    UserCardDelayEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
