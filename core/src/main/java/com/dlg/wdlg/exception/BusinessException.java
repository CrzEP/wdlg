package com.dlg.wdlg.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }
    public BusinessException(String message) {
        super(message);
    }

}
