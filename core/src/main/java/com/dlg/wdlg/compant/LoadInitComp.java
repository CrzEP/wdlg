package com.dlg.wdlg.compant;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadInitComp implements Runnable{

    @Resource
    LoadFromBaiCi loadFromBaiCi;
    @Resource
    LoadFromCsv  loadFromCsv;

    Thread thread = new Thread(this);

    @PostConstruct
    public void init(){
        thread.setName("LoadInitComp");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.error("LoadInitComp error: ",e);
            }
        });
        thread.start();
    }

    @Override
    public void run() {
//        loadFromBaiCi.startReq();
        loadFromCsv.startReq();
    }
}
