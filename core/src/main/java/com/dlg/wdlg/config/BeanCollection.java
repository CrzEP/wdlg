package com.dlg.wdlg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BeanCollection {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
