package com.dlg.wdlg.cmds;

import com.dlg.wdlg.comm.GConst;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class InfoCommands {

    @ShellMethod("v")
    public String hi() {
        return "version: " + GConst.VERSION;
    }

}
