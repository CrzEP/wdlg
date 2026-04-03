package com.dlg.wdlg.config.configValue;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Data
@Component
@ConfigurationProperties(prefix = "wdlg.memory")
public class MemoryValue {

    /**
     * 复习时间随机化： 提前延后 5～10%
     * false:  精确间隔、可预测
     */
    @Value("${enable-fuzzing:true}")
    private Boolean enableFuzzing;

}
