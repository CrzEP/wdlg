package com.dlg.wdlg.config.configValue;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Data
@Component
@ConfigurationProperties(prefix = "wdlg.baidu-dev")
public class BaiduValue {

    private String appId;

    private String keySecret;
}
