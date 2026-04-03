package com.dlg.wdlg.config;

import com.dlg.wdlg.compant.BaiduTranslateRest;
import com.dlg.wdlg.config.configValue.BaiduValue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BeanCollection {

    @Resource
    private BaiduValue baiduValue;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BaiduTranslateRest baiduTranslateRest() {
        return BaiduTranslateRest.INSTANCE(baiduValue.getAppId(),
                baiduValue.getKeySecret());
    }

}
