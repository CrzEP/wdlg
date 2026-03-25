package com.dlg.wdlg.controller;

import com.dlg.wdlg.comm.GConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class InfoController {

    @GetMapping("/version")
    public String hi() {
        return "version: " + GConst.VERSION;
    }

}
