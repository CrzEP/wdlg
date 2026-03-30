package com.dlg.wdlg.cmds;

import com.dlg.wdlg.api.NetApiRestApi;
import com.dlg.wdlg.comm.GConst;
import jakarta.annotation.Resource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class InfoCommands {

    @Resource
    NetApiRestApi netApiRestApi;

    @ShellMethod("v")
    public String v() {
        return "version: " + GConst.VERSION;
    }

    @ShellMethod("ts")
    public String ts(@ShellOption("q") String q) {
        return netApiRestApi.translate(q);
    }


}
