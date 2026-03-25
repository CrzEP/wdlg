package com.dlg.wdlg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wdlg")
public class WdlgValue {

    /**
     * 本地词汇储存目录
     */
    private String localWordDir;



}
