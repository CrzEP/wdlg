package com.dlg.wdlg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.dlg.wdlg.mapper")
@SpringBootApplication(scanBasePackages = "com.dlg.wdlg")
public class GwordApplication  {

    public static void main(String[] args) {
        SpringApplication.run(GwordApplication.class, args);
    }

}
