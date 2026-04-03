package com.dlg.wdlg.compant;

import com.dlg.wdlg.api.LoadWordService;
import com.dlg.wdlg.config.configValue.WdlgValue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadInitComp implements Runnable {

    @Resource
    WdlgValue wdlgValue;
    @Resource
    LoadWordService loadWordService;

    Thread thread = new Thread(this);

    @PostConstruct
    public void init() {
        thread.setName("LoadInitComp");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.error("LoadInitComp error: ", e);
            }
        });
        thread.start();
    }

    @Override
    public void run() {
        if (!wdlgValue.getInitCompLoading()) {
            return;
        }
        log.info("LoadInitComp 初始化组件 run...");
        String filePath = wdlgValue.getLocalWordLoadFilePath();
        loadWordService.loadFromJsonWordFile(filePath);
    }

}
