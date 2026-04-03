package com.dlg.wdlg.config.configValue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Setter
@Component
@ConfigurationProperties(prefix = "wdlg")
public class WdlgValue {

    @Getter
    private String baseDir;

    private String appSecretKey;

    /**
     * 本地词汇储存目录
     */
    private String localWordSaveDir;

    /**
     * 是否初始化
     */
    @Getter
    @Value("${initCompLoading:false}")
    private Boolean initCompLoading;

    private String localWordLoadFilePath;

    private String baiciLoadDir;

    public String getLocalWordSaveDir() {
        return baseDir + localWordSaveDir;
    }

    public String getLocalWordLoadFilePath() {
        return baseDir + localWordLoadFilePath;
    }

    public String getAppSecretKey() {
        String appKey = "wdlg";
        return appSecretKey + appKey;
    }
}
