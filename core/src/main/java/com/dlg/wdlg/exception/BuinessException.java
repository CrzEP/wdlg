package com.dlg.wdlg.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuinessException extends RuntimeException {

    public BuinessException() {
    }
    public BuinessException(String message) {
        super(message);
    }

}
