package com.dlg.wdlg.comm;

public enum AvailableEnum implements IterEnum<Integer> {

    ENABLE(GConst.ENABLE),

    DISABLE(GConst.DISABLE);

    private final int code;

    AvailableEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
