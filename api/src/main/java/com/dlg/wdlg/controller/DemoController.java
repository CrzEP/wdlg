package com.dlg.wdlg.controller;

import com.dlg.wdlg.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class DemoController {

    @Autowired
    TagService tagService;

    @GetMapping("/hi")
    public String hi(){
        tagService.saveTag();
        return "hi"+System.currentTimeMillis();
    }

}
